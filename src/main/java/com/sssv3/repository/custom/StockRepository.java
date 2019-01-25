package com.sssv3.repository.custom;

import com.sssv3.domain.MLogType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<MLogType, Long> {

    /*@Query(value = "SELECT t.id, t.nama, sum(qty) qty " +
        ", sum(mlog.diameter * mlog.panjang) / 1000000 volume"+
        " FROM m_log mlog\n" +
        "\tjoin m_log_category cat on mlog.mlogcat_id = cat.id\n" +
        "    join m_log_type t on cat.mlogtype_id = t.id\n" +
        " group by t.id ORDER BY t.id "
        , countQuery = "SELECT count(*)\n" +
        "\tFROM m_log mlog\n" +
        "\tjoin m_log_category cat on mlog.mlogcat_id = cat.id\n" +
        "    join m_log_type t on cat.mlogtype_id = t.id\n" +
        "group by t.id "
        , nativeQuery = true)*/
    //Log
    @Query(value = "SELECT t.id, t.nama, sum(qty) qty " +
        ", sum(mlog.diameter * mlog.panjang) / 1000000 volume"+
        " FROM m_log mlog\n" +
        "\tjoin m_log_category cat on mlog.mlogcat_id = cat.id\n" +
        "    join m_log_type t on cat.mlogtype_id = t.id\n" +
        " group by t.id "
        , countQuery = "SELECT count(*)\n" +
        "\tFROM m_log mlog\n" +
        "\tjoin m_log_category cat on mlog.mlogcat_id = cat.id\n" +
        "    join m_log_type t on cat.mlogtype_id = t.id\n" +
        "group by t.id "
        , nativeQuery = true)
    Page<Object[]> findLogStockGroup(Pageable pageable);

    /*@Query(value = "SELECT cat.id, cat.nama, sum(qty) \n" +
        "\t, sum(mlog.diameter * mlog.panjang) / 1000000 volume\n" +
        "\t, cat.harga_beli\n" +
        "\tFROM m_log mlog\n" +
        "\tjoin m_log_category cat on mlog.mlogcat_id = cat.id\n" +
        "    where cat.mlogtype_id =  :id \n" +
        "group by cat.id "
        , countQuery = "SELECT count(*)\n" +
        "\tFROM m_log mlog\n" +
        "\tjoin m_log_category cat on mlog.mlogcat_id = cat.id\n" +
        "    where cat.mlogtype_id =  :id \n" +
        "group by cat.id "
        , nativeQuery = true)*/
    @Query(value = "SELECT mlog.id, mlog.nama, qty\n" +
        "\t, (mlog.diameter * mlog.panjang) / 1000000 as volume\n" +
        "\t, mlog.harga_beli\n" +
        "\tFROM m_log mlog\n" +
        "\tjoin m_log_category cat on mlog.mlogcat_id = cat.id\n" +
        "    where cat.mlogtype_id = :id "
        , countQuery = "SELECT count(*) " +
        "\tFROM m_log mlog\n" +
        "\tjoin m_log_category cat on mlog.mlogcat_id = cat.id\n" +
        "    where cat.mlogtype_id = :id "
        , nativeQuery = true)
    Page<Object[]> findLogStockGroupDtl(@Param("id") Long id, Pageable pageable);

    //Veneer
    @Query(value = "SELECT cat.jhi_type\n" +
        "\t, sum(qty) + sum(qty_produksi)  qtytotal\n" +
        "    , sum(cat.tebal * cat.panjang * cat.lebar * (qty + qty_produksi)) / 1000000000 volumetotal\n" +
        "\t, sum(qty) qty, sum(qty_produksi) qty_produksi\n" +
        "    from m_veneer_Category cat\n" +
        "\tjoin m_veneer m on m.veneercategory_id = cat.id\n" +
        " group by cat.jhi_type "
        , countQuery = "SELECT count(*)\n" +
        "    from m_veneer_Category cat\n" +
        "\tjoin m_veneer m on m.veneercategory_id = cat.id\n" +
        " group by cat.jhi_type "
        , nativeQuery = true)
    Page<Object[]> findVeneerStockGroup(Pageable pageable);

    @Query(value = "SELECT cat.id, cat.nama\n" +
        "\t, sum(qty) + sum(qty_produksi)  qtytotal\n" +
        "    , sum(cat.tebal * cat.panjang * cat.lebar * (qty + qty_produksi)) / 1000000000 volumetotal\n" +
        "\t, m.harga_beli , jhi_type as type , subtype\n" +
        "    from m_veneer_Category cat\n" +
        "\tjoin m_veneer m on m.veneercategory_id = cat.id\n" +
        " where jhi_type = :type\n" +
        " group by cat.id\n" +
        " order by cat.nama "
        , countQuery = "SELECT count(*)\n" +
        "    from m_veneer_Category cat\n" +
        "\tjoin m_veneer m on m.veneercategory_id = cat.id\n" +
        " where jhi_type = :type \n" +
        " group by cat.id\n"
        , nativeQuery = true)
    Page<Object[]> findVeneerStockGroupDtl(@Param("type") String type, Pageable pageable);

    @Query(value = "SELECT cat.subtype\n" +
        "\t, sum(qty) + sum(qty_produksi)  qtytotal\n" +
        "    , sum(cat.tebal * cat.panjang * cat.lebar * (qty + qty_produksi)) / 1000000000 volumetotal\n" +
        "\t, sum(qty) qty, sum(qty_produksi) qty_produksi\n" +
        "    from m_veneer_Category cat\n" +
        "\tjoin m_veneer m on m.veneercategory_id = cat.id\n" +
        "    where jhi_type = :type\n" +
        " group by cat.subtype "
        , countQuery = "SELECT count(*)\n" +
        "    from m_veneer_Category cat\n" +
        "\tjoin m_veneer m on m.veneercategory_id = cat.id\n" +
        "    where jhi_type = :type\n" +
        " group by cat.subtype "
        , nativeQuery = true)
    Page<Object[]> findVeneerStockSubtype(@Param("type") String type, Pageable pageable);

    @Query(value = "SELECT cat.id, cat.nama\n" +
        "\t, sum(qty) + sum(qty_produksi)  qtytotal\n" +
        "    , sum(cat.tebal * cat.panjang * cat.lebar * (qty + qty_produksi)) / 1000000000 volumetotal\n" +
        "\t, m.harga_beli\n" +
        "    from m_veneer_Category cat\n" +
        "\tjoin m_veneer m on m.veneercategory_id = cat.id\n" +
        " where jhi_type = :type AND subtype = :subtype\n" +
        " group by cat.id\n" +
        " order by cat.nama "
        , countQuery = "SELECT count(*)\n" +
        "    from m_veneer_Category cat\n" +
        "\tjoin m_veneer m on m.veneercategory_id = cat.id\n" +
        " where jhi_type = :type AND subtype = :subtype\n" +
        " group by cat.id\n" +
        " order by cat.nama "
        , nativeQuery = true)
    Page<Object[]> findVeneerStockSubtypeDtl(@Param("type") String type, @Param("subtype") String subtype, Pageable pageable);

    //Plywood
    @Query(value= "select gr.id, gr.nama \n" +
        "\t, sum(qty) + sum(qty_produksi) qtytotal\n" +
        "    , sum(cat.tebal * cat.panjang * cat.lebar * (qty + qty_produksi)) / 1000000000 volumetotal\n" +
        "    from m_plywood_grade gr\n" +
        "    join m_plywood_category cat on gr.id = cat.plywoodgrade_id\n" +
        "    join m_plywood m on m.plywoodcategory_id = cat.id\n" +
        " group by gr.id "
        , countQuery = "select count(*)" +
        "    from m_plywood_grade gr\n" +
        "    join m_plywood_category cat on gr.id = cat.plywoodgrade_id\n" +
        "    join m_plywood m on m.plywoodcategory_id = cat.id\n" +
        " group by gr.id "
        , nativeQuery = true)
    Page<Object[]> findPlywoodStockGroup(Pageable pageable);

    @Query(value = "select cat.id, cat.nama \n" +
        "\t, sum(qty) + sum(qty_produksi) qtytotal\n" +
        "    , sum(cat.tebal * cat.panjang * cat.lebar * (qty + qty_produksi)) / 1000000000 volumetotal\n" +
        "    , m.harga_beli\n" +
        "    from m_plywood_grade gr\n" +
        "    join m_plywood_category cat on gr.id = cat.plywoodgrade_id\n" +
        "    join m_plywood m on m.plywoodcategory_id = cat.id\n" +
        "WHERE gr.id = :id \n" +
        "group by cat.id\n" +
        "order by cat.nama "
        , countQuery = "select count(*)" +
        "    from m_plywood_grade gr\n" +
        "    join m_plywood_category cat on gr.id = cat.plywoodgrade_id\n" +
        "    join m_plywood m on m.plywoodcategory_id = cat.id\n" +
        "WHERE gr.id = :id \n" +
        "group by cat.id\n "
        , nativeQuery = true)
    Page<Object[]> findPlywoodStockGroupDtl(@Param("id") Long id, Pageable pageable);
}
