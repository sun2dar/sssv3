package com.sssv3.web.rest.custom;

import com.codahale.metrics.annotation.Timed;
import com.sssv3.domain.wrapper.StockDetailWrapper;
import com.sssv3.domain.wrapper.StockLogWrapper;
import com.sssv3.domain.wrapper.StockVeneerDtlWrapper;
import com.sssv3.domain.wrapper.StockVeneerWrapper;
import com.sssv3.service.custom.StockService;
import com.sssv3.web.rest.MLogCategoryResource;
import com.sssv3.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StockResource {
    private final Logger log = LoggerFactory.getLogger(MLogCategoryResource.class);

    private static final String ENTITY_NAME = "StockInquiry";

    @Autowired
    private StockService service;

    @GetMapping("/stock/log")
    @Timed
    public ResponseEntity<Page<StockLogWrapper>> getStockLog(Pageable pageable) {
        log.debug("REST request to get a page of Log's Stock");
        Page<Object[]> pageData = service.findLogStockByType(pageable);
        //List<StockLogWrapper> listContent = log2StockWrapper(pageData.getContent());
        Page<StockLogWrapper> listContent = new PageImpl<StockLogWrapper>(log2StockWrapper(pageData.getContent()), pageData.getPageable(), pageData.getSize() );

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(pageData, "/stock/log");
        return ResponseEntity.ok().headers(headers).body(listContent);
    }

    @GetMapping("/stock/log/{id}")
    @Timed
    public ResponseEntity<Page<StockDetailWrapper>> getStockLogDtl(@PathVariable Long id, Pageable pageable) {
        log.debug("REST request to get Stock Log TypeID : {}", id);
        Page<Object[]> pageData = service.findLogStockByTypeDtl(pageable, id);
        //List<StockDetailWrapper> listContent = convertStockDetail(pageData.getContent());
        Page<StockDetailWrapper> listContent = new PageImpl<StockDetailWrapper>(convertStockDetail(pageData.getContent()), pageData.getPageable(), pageData.getSize() );

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(pageData, "/stock/log/{id}");
        return ResponseEntity.ok().headers(headers).body(listContent);
    }

    /*@GetMapping("/stock/veneer")
    @Timed
    public ResponseEntity<List<StockVeneerWrapper>> getStockVeneer(Pageable pageable) {
        log.debug("REST request to get a page of Veneer's Stock");
        Page<Object[]> pageData = service.findVeneerStockByType(pageable);
        List<StockVeneerWrapper> listContent = veneer2Wrapper(pageData.getContent());

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(pageData, "/stock/log");
        return ResponseEntity.ok().headers(headers).body(listContent);
    }*/

    @GetMapping("/stock/veneer")
    @Timed
    public ResponseEntity<Page<StockVeneerWrapper>> getStockVeneer(Pageable pageable) {
        log.debug("REST request to get a page of Veneer's Stock");
        Page<Object[]> pageData = service.findVeneerStockByType(pageable);
        //List<StockVeneerWrapper> listContent = veneer2Wrapper(pageData.getContent());
        Page<StockVeneerWrapper> listContent = new PageImpl<StockVeneerWrapper>(veneer2Wrapper(pageData.getContent()), pageData.getPageable(), pageData.getSize() );

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(pageData, "/stock/log");
        return ResponseEntity.ok().headers(headers).body(listContent);
    }

    @GetMapping("/stock/veneer/{type}")
    @Timed
    public ResponseEntity<Page<StockVeneerDtlWrapper>> getStockVeneerDtl(@PathVariable String type, Pageable pageable) {
        log.debug("REST request to get Stock Veneer TypeID : {}", type);
        Page<Object[]> pageData = service.findVeneerStockDtl(pageable, type);
        //List<StockDetailWrapper> listContent = convertStockDetail(pageData.getContent());
        Page<StockVeneerDtlWrapper> listContent = new PageImpl<StockVeneerDtlWrapper>(convertVeneerDtl(pageData.getContent()), pageData.getPageable(), pageData.getSize() );

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(pageData, "/stock/veneer/{id}");
        return ResponseEntity.ok().headers(headers).body(listContent);
    }

    /*@GetMapping("/stock/veneer/dtl")
    @Timed
    public ResponseEntity<Page<StockDetailWrapper>> getStockVeneerBySubtypeDtl(
        @RequestParam(value = "type") String type,
        @RequestParam(value = "subtype") String subtype,
        Pageable pageable) {
        log.debug("REST request to get Stock Veneer Type : "+ type + " and subtype : " + subtype);
        Page<Object[]> pageData = service.findVeneerStockBySubtypeDtl(pageable, type, subtype);
        //List<StockDetailWrapper> listContent = convertStockDetail(pageData.getContent());
        Page<StockDetailWrapper> listContent = new PageImpl<StockDetailWrapper>(convertStockDetail(pageData.getContent()), pageData.getPageable(), pageData.getSize() );

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(pageData, "/stock/veneer/dtl");
        return ResponseEntity.ok().headers(headers).body(listContent);
    }
*/
    @GetMapping("/stock/plywood")
    @Timed
    public ResponseEntity<Page<StockLogWrapper>> getStockPlywood(Pageable pageable) {
        log.debug("REST request to get a page of Plywood's Stock");
        Page<Object[]> pageData = service.findPlywoodStockByType(pageable);
        //List<StockLogWrapper> listContent = log2StockWrapper(pageData.getContent());
        Page<StockLogWrapper> listContent = new PageImpl<StockLogWrapper>(log2StockWrapper(pageData.getContent()), pageData.getPageable(), pageData.getSize() );

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(pageData, "/stock/plywood");
        return ResponseEntity.ok().headers(headers).body(listContent);
    }

    @GetMapping("/stock/plywood/{id}")
    @Timed
    public ResponseEntity<Page<StockDetailWrapper>> getStockPlywoodDtl(@PathVariable Long id, Pageable pageable) {
        log.debug("REST request to get Stock Plywood TypeID : {}", id);
        Page<Object[]> pageData = service.findPlywoodStockByTypeDtl(pageable, id);
        //List<StockDetailWrapper> listContent = convertStockDetail(pageData.getContent());
        Page<StockDetailWrapper> listContent = new PageImpl<StockDetailWrapper>(convertStockDetail(pageData.getContent()), pageData.getPageable(), pageData.getSize() );

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(pageData, "/stock/veneer/{id}");
        return ResponseEntity.ok().headers(headers).body(listContent);
    }


    ////Function convert to wrapper model
    private List<StockLogWrapper> log2StockWrapper(List<Object[]> lsData) {
        List<StockLogWrapper> lsReturn = new ArrayList<>();

        for (Object[] data : lsData) {
            StockLogWrapper f = new StockLogWrapper();
            f.setId((BigInteger) data[0]);
            f.setName((String) data[1]);
            f.setQty((Double) data[2]);
            f.setVolume((Double) data[3]) ;
            f.setFlag(false);
            lsReturn.add(f);
        }
        return lsReturn;
    }

    private List<StockVeneerWrapper> veneer2Wrapper(List<Object[]> lsData) {
        List<StockVeneerWrapper> lsReturn = new ArrayList<>();

        for (Object[] data : lsData) {
            StockVeneerWrapper f = new StockVeneerWrapper();
            f.setName((String) data[0]);
            f.setQty((Double) data[1]);
            f.setVolume((Double) data[2]);
            f.setFlag(false);
            lsReturn.add(f);
        }
        return lsReturn;
    }

    private List<StockDetailWrapper> convertStockDetail(List<Object[]> lsData) {
        List<StockDetailWrapper> lsReturn = new ArrayList<>();

        for (Object[] data : lsData) {
            StockDetailWrapper f = new StockDetailWrapper();
            f.setId((BigInteger) data[0]);
            f.setName((String) data[1]);
            f.setQty((Double) data[2]);
            f.setVolume((Double) data[3]) ;
            f.setHargabeli((Double) data[4]);
            f.setFlag(false);
            lsReturn.add(f);
        }
        return lsReturn;
    }

    private List<StockVeneerDtlWrapper> convertVeneerDtl(List<Object[]> lsData) {
        List<StockVeneerDtlWrapper> lsReturn = new ArrayList<>();

        for (Object[] data : lsData) {
            StockVeneerDtlWrapper f = new StockVeneerDtlWrapper();
            f.setId((BigInteger) data[0]);
            f.setName((String) data[1]);
            f.setQty((Double) data[2]);
            f.setVolume((Double) data[3]) ;
            f.setHargabeli((Double) data[4]);
            f.setType((String) data[5]);
            f.setSubtype((String) data[6]);
            f.setFlag(false);
            lsReturn.add(f);
        }
        return lsReturn;
    }

    private Page<StockLogWrapper> toStockPage(Page<Object[]> pg){
        Page<StockLogWrapper> res = new PageImpl<StockLogWrapper>(log2StockWrapper(pg.getContent()), pg.getPageable(), pg.getSize() );

        return res;
    }
}
