package com.imooc.ad.service.impl;

import com.imooc.ad.constant.Constants;
import com.imooc.ad.dao.AdPlanRepository;
import com.imooc.ad.dao.AdUnitRepository;
import com.imooc.ad.dao.CreativeRepository;
import com.imooc.ad.dao.condition.AdUnitDistrictRepository;
import com.imooc.ad.dao.condition.AdUnitItRepository;
import com.imooc.ad.dao.condition.AdUnitKeywordRepository;
import com.imooc.ad.dao.condition.CreativeUnitRepository;
import com.imooc.ad.entity.AdPlan;
import com.imooc.ad.entity.AdUnit;
import com.imooc.ad.entity.condition.AdUnitDistrict;
import com.imooc.ad.entity.condition.AdUnitIt;
import com.imooc.ad.entity.condition.AdUnitKeyword;
import com.imooc.ad.entity.condition.CreativeUnit;
import com.imooc.ad.exception.AdException;
import com.imooc.ad.service.IAdUnitService;
import com.imooc.ad.vo.AdUnitDistrictRequest;
import com.imooc.ad.vo.AdUnitDistrictResponse;
import com.imooc.ad.vo.AdUnitItRequest;
import com.imooc.ad.vo.AdUnitItResponse;
import com.imooc.ad.vo.AdUnitKeywordRequest;
import com.imooc.ad.vo.AdUnitKeywordResponse;
import com.imooc.ad.vo.AdUnitRequest;
import com.imooc.ad.vo.AdUnitResponse;
import com.imooc.ad.vo.CreativeUnitRequest;
import com.imooc.ad.vo.CreativeUnitResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Seven
 * @date 2019/10/19 9:59
 * @description 推广单元的业务逻辑层接口实现类
 */
@Slf4j
@Service
public class AdUnitServiceImpl implements IAdUnitService {
    /**
     * 注入持久层对象
     */
    private final AdPlanRepository planRepository;
    private final AdUnitRepository unitRepository;
    private final AdUnitKeywordRepository unitKeywordRepository;
    private final AdUnitItRepository unitItRepository;
    private final AdUnitDistrictRepository unitDistrictRepository;
    private final CreativeRepository creativeRepository;
    private final CreativeUnitRepository creativeUnitRepository;

    @Autowired
    public AdUnitServiceImpl(AdPlanRepository planRepository, AdUnitRepository unitRepository,
                             AdUnitKeywordRepository unitKeywordRepository, AdUnitItRepository unitItRepository,
                             AdUnitDistrictRepository unitDistrictRepository, CreativeRepository creativeRepository,
                             CreativeUnitRepository creativeUnitRepository) {
        this.planRepository = planRepository;
        this.unitRepository = unitRepository;
        this.unitKeywordRepository = unitKeywordRepository;
        this.unitItRepository = unitItRepository;
        this.unitDistrictRepository = unitDistrictRepository;
        this.creativeRepository = creativeRepository;
        this.creativeUnitRepository = creativeUnitRepository;
    }

    @Override
    @Transactional(rollbackFor = AdException.class)
    public AdUnitResponse createUnit(AdUnitRequest request) throws AdException {
        if (!request.createValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        // 先看计划是否存在
        Optional<AdPlan> plan = planRepository.findById(request.getPlanId());
        if (!plan.isPresent()) {
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }

        // 是否存在同名推广计划
        AdUnit adUnit = unitRepository.findByPlanIdAndUnitName(request.getPlanId(), request.getUnitName());
        if (null != adUnit) {
            throw new AdException(Constants.ErrorMsg.SAME_NAME_UNIT_ERROR);
        }

        // 保存并BeanUtils属性复制
        AdUnit saveAdUnit = unitRepository.save(
                new AdUnit(request.getPlanId(),
                        request.getUnitName(),
                        request.getPositionType(),
                        request.getBudget()));
        AdUnitResponse response = new AdUnitResponse();
        BeanUtils.copyProperties(saveAdUnit, response);

        return response;
    }

    @Override
    @Transactional(rollbackFor = AdException.class)
    public AdUnitKeywordResponse createUnitKeyword(AdUnitKeywordRequest request) throws AdException {
        // java8流式获取数据——》获取ids集合，只是Long的id
        Stream<AdUnitKeywordRequest.UnitKeyword> stream = request.getUnitKeywords().stream();
        Stream<Long> longStream = stream.map(AdUnitKeywordRequest.UnitKeyword::getUnitId);
        getIdsAndCheck(longStream);

        // 数据存储对象
        List<Long> ids = Collections.emptyList();
        ArrayList<AdUnitKeyword> adUnitKeywords = new ArrayList<>();

        // 将对象添加到集合中并持久化
        if (!CollectionUtils.isEmpty(request.getUnitKeywords())) {
            request.getUnitKeywords().forEach(i -> adUnitKeywords.add(
                    new AdUnitKeyword(i.getUnitId(), i.getKeyword())
            ));
            // 存储并解析成返回对象xxxResponse
            List<AdUnitKeyword> adUnitKeywordsSave = unitKeywordRepository.saveAll(adUnitKeywords);
            ids = adUnitKeywordsSave.stream().map(AdUnitKeyword::getId).collect(Collectors.toList());
        }

        return new AdUnitKeywordResponse(ids);
    }

    @Override
    @Transactional(rollbackFor = AdException.class)
    public AdUnitItResponse createUnitIt(AdUnitItRequest request) throws AdException {
        // java8流式获取数据——》获取ids集合，只是Long的id
        Stream<Long> longStream = request.getUnitIts().stream().map(AdUnitItRequest.UnitIt::getUnitId);
        getIdsAndCheck(longStream);

        // 数据存储对象
        List<Long> ids = Collections.emptyList();
        ArrayList<AdUnitIt> adUnitIts = new ArrayList<>();

        // 将对象添加到集合中并持久化
        if (!CollectionUtils.isEmpty(request.getUnitIts())) {
            request.getUnitIts().forEach(i -> adUnitIts.add(
                    new AdUnitIt(i.getUnitId(), i.getItTag())
            ));
            // 存储并解析成返回对象xxxResponse
            ids = unitItRepository.saveAll(adUnitIts).stream().map(AdUnitIt::getId)
                    .collect(Collectors.toList());
        }

        return new AdUnitItResponse(ids);
    }

    @Override
    @Transactional(rollbackFor = AdException.class)
    public AdUnitDistrictResponse createUnitDistrict(AdUnitDistrictRequest request) throws AdException {
        // java8流式获取数据——》获取ids集合，只是Long的id
        Stream<Long> longStream = request.getUnitDistricts().stream().map(AdUnitDistrictRequest.UnitDistrict::getUnitId);
        getIdsAndCheck(longStream);

        //数据存储对象
        List<Long> ids = Collections.emptyList();
        ArrayList<AdUnitDistrict> adUnitDistricts = new ArrayList<>();

        // 将对象添加到集合中并持久化
        if (!CollectionUtils.isEmpty(request.getUnitDistricts())) {
            request.getUnitDistricts().forEach(i -> adUnitDistricts.add(
                    new AdUnitDistrict(i.getUnitId(), i.getProvince(), i.getCity())
            ));
            // 存储并解析成返回对象xxxResponse
            ids = unitDistrictRepository.saveAll(adUnitDistricts).stream().map(AdUnitDistrict::getId)
                    .collect(Collectors.toList());
        }

        return new AdUnitDistrictResponse(ids);
    }

    @Override
    @Transactional(rollbackFor = AdException.class)
    public CreativeUnitResponse createCreativeUnit(CreativeUnitRequest request) throws AdException {
        // 两个ids的list判断
        Stream<Long> longStreamCreative = request.getCreativeUnitItem().stream()
                .map(CreativeUnitRequest.CreativeUnitItem::getCreativeId);
        Stream<Long> longStreamUnit = request.getCreativeUnitItem().stream()
                .map(CreativeUnitRequest.CreativeUnitItem::getUnitId);
        getCreativeAndUnitIdsAndCheck(longStreamCreative, longStreamUnit);

        // 存储对象
        List<Long> ids = Collections.emptyList();
        ArrayList<CreativeUnit> creativeUnits = new ArrayList<>();

        // 将对象添加到集合中并持久化
        if (!CollectionUtils.isEmpty(request.getCreativeUnitItem())) {
            request.getCreativeUnitItem().forEach(i -> creativeUnits.add(
                    new CreativeUnit(i.getCreativeId(), i.getUnitId())
            ));
            ids = creativeUnitRepository.saveAll(creativeUnits).stream().map(CreativeUnit::getId)
                    .collect(Collectors.toList());
        }

        return new CreativeUnitResponse(ids);
    }

    /**
     * 推广单元的传入流式id列表，并验证有效性
     *
     * @param longStream
     * @throws AdException
     */
    private void getIdsAndCheck(Stream<Long> longStream) throws AdException {
        List<Long> idsStream = longStream.collect(Collectors.toList());

        if (isRelateUnitExist(idsStream)) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
    }

    /**
     * 验证传递的 unitIds 列表是有存储对应的推广单元信息
     *
     * @param ids
     * @return
     */
    private boolean isRelateUnitExist(List<Long> ids) {
        // 参数为空校验
        if (CollectionUtils.isEmpty(ids)) {
            return false;
        }

        // ids去重操作
        HashSet<Long> longs = new HashSet<>(ids);

        // 去重的推广单元set中可能有不存在的推广单元id
        return unitRepository.findAllById(ids).size() == longs.size();
    }

    /**
     * 创意与推广单元的传入流式id列表，并验证有效性
     *
     * @param longStreamCreative
     * @param longStreamUnit
     * @throws AdException
     */
    private void getCreativeAndUnitIdsAndCheck(Stream<Long> longStreamCreative, Stream<Long> longStreamUnit) throws AdException {
        List<Long> idsStreamCreative = longStreamCreative.collect(Collectors.toList());
        List<Long> idsStreamUnit = longStreamUnit.collect(Collectors.toList());

        if (isRelateCreativeExist(idsStreamCreative) && isRelateUnitExist(idsStreamUnit)) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
    }

    /**
     * 验证传递的 creativeIds 列表是有存储对应的创意信息
     *
     * @param ids
     * @return
     */
    private boolean isRelateCreativeExist(List<Long> ids) {
        // 参数为空校验
        if (CollectionUtils.isEmpty(ids)) {
            return false;
        }

        // ids去重操作
        HashSet<Long> longs = new HashSet<>(ids);

        // 去重的推广单元set中可能有不存在的推广单元id
        return creativeRepository.findAllById(ids).size() == longs.size();
    }

}
