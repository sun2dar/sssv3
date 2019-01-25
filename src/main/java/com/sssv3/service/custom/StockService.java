package com.sssv3.service.custom;

import com.sssv3.repository.custom.StockRepository;
import com.sssv3.service.impl.MLogTypeServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class StockService {

    private final Logger log = LoggerFactory.getLogger(MLogTypeServiceImpl.class);

    @Autowired
    private StockRepository repo;

    public Page<Object[]> findLogStockByType(Pageable pageable){
        log.debug("Request to get all Stock of log group by type");
        Page<Object[]> lsData = repo.findLogStockGroup(pageable);

        return lsData;
    }

    public Page<Object[]> findLogStockByTypeDtl(Pageable pageable, Long id){
        log.debug("Request to get all Stock of log group by type");
        Page<Object[]> lsData = repo.findLogStockGroupDtl(id, pageable);

        return lsData;
    }

    //Veneer
    public Page<Object[]> findVeneerStockByType(Pageable pageable){
        log.debug("Request to get all Veneer of log group by type - BASAH/KERING/REPAIR");
        Page<Object[]> lsData = repo.findVeneerStockGroup(pageable);

        return lsData;
    }

    public Page<Object[]> findVeneerStockDtl(Pageable pageable, String type){
        log.debug("Request to get all Stock of log group by type - LC/SC/FB");
        Page<Object[]> lsData = repo.findVeneerStockGroupDtl(type, pageable);

        return lsData;
    }

    /*public Page<Object[]> findVeneerStockBySubtype(Pageable pageable, String type){
        log.debug("Request to get all Stock of log group by subtype - LC/SC/FB");
        Page<Object[]> lsData = repo.findVeneerStockSubtype(type, pageable);

        return lsData;
    }

    public Page<Object[]> findVeneerStockBySubtypeDtl(Pageable pageable, String type, String subtype){
        log.debug("Request to get all Stock of log sub type detail");
        Page<Object[]> lsData = repo.findVeneerStockSubtypeDtl(type, subtype, pageable);

        return lsData;
    }*/

    //Plywood
    public Page<Object[]> findPlywoodStockByType(Pageable pageable){
        log.debug("Request to get all Plywood of log group by type");
        Page<Object[]> lsData = repo.findPlywoodStockGroup(pageable);

        return lsData;
    }

    public Page<Object[]> findPlywoodStockByTypeDtl(Pageable pageable, Long id){
        log.debug("Request to get all Stock of log group by type");
        Page<Object[]> lsData = repo.findPlywoodStockGroupDtl(id, pageable);

        return lsData;
    }
}
