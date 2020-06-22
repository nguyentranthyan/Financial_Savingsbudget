package com.example.financial_savings.templates.transactions.edit_transaction;

import android.app.Activity;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.financial_savings.R;
import com.example.financial_savings.entities.DanhMuc;
import com.example.financial_savings.entities.SoGiaoDich;
import com.example.financial_savings.entities.SuKien;
import com.example.financial_savings.entities.ViCaNhan;
import com.example.financial_savings.helper.DBHelper;
import com.example.financial_savings.templates.transactions.add_transaction.EventSaveTemplate;

public class EventUpdateTemplate {

    public static void handlingSave(double money, java.sql.Date date, String note, SoGiaoDich soGiaoDich, DanhMuc danhMuc,
                                    SuKien suKien, Activity activity, DBHelper dbHelper, String DOANHTHU, String remind,
                                    String repeat, CheckBox checkBox) {
        updateMoneyWalletOld(soGiaoDich, dbHelper, DOANHTHU);
        soGiaoDich.setSoTien(money);
        soGiaoDich.setNgayGiaoDich(date);
        soGiaoDich.setGhiChu(note);
        soGiaoDich.setMaDanhMuc(danhMuc.getMaDanhMuc());
        soGiaoDich.setLapLai(repeat);
        handlinEvent(suKien, soGiaoDich);
        EventSaveTemplate.handlingRemind(remind, soGiaoDich);
        EventSaveTemplate.handlingSetStatus(soGiaoDich, checkBox);
        EventSaveTemplate.handlingCheckStatus(dbHelper, soGiaoDich);
        dbHelper.update_SoGiaoDich(soGiaoDich);
        updateMoneyWalletNew(money, danhMuc.getMaDanhMuc(), soGiaoDich.getMaVi(), dbHelper, DOANHTHU);
        EventSaveTemplate.handlinAlamrRemind(soGiaoDich, activity, dbHelper);
        EventSaveTemplate.handlingAlarmRepeat(soGiaoDich, activity, dbHelper);
        activity.onBackPressed();
        Toast.makeText(activity, R.string.success_trans_edit, Toast.LENGTH_SHORT).show();
    }

    private static void handlinEvent(SuKien suKien, SoGiaoDich soGiaoDich) {
        if(suKien == null) {
            soGiaoDich.setMaSuKien("null");
        }
        else {
            soGiaoDich.setMaSuKien(suKien.getMaSuKien());
        }
    }

    private static void updateMoneyWalletNew(double money, String idCate, String idWallet, DBHelper dbHelper, String DOANHTHU) {
        ViCaNhan viCaNhan = dbHelper.getByID_ViCaNhan(idWallet);
        DanhMuc danhmuc = dbHelper.getByID_DanhMuc(idCate);
        if(danhmuc.getLoaiDanhMuc().equals(DOANHTHU)) {
            viCaNhan.napTien(String.valueOf(money));
        }
        else {
            viCaNhan.rutTien(String.valueOf(money));
        }
        dbHelper.update_ViCaNhan(viCaNhan);
    }

    private static void updateMoneyWalletOld(SoGiaoDich soGiaoDich, DBHelper dbHelper, String DOANHTHU) {
        ViCaNhan viCaNhan = handlingUpdateWallet(soGiaoDich, dbHelper, DOANHTHU);
        dbHelper.update_ViCaNhan(viCaNhan);
    }

    public static ViCaNhan handlingUpdateWallet(SoGiaoDich soGiaoDich, DBHelper dbHelper, String DOANHTHU) {
        double moneyOld = soGiaoDich.getSoTien();
        ViCaNhan viCaNhan = dbHelper.getByID_ViCaNhan(soGiaoDich.getMaVi());
        DanhMuc danhMucOld = dbHelper.getByID_DanhMuc(soGiaoDich.getMaDanhMuc());
        if(danhMucOld.getLoaiDanhMuc().equals(DOANHTHU)) {
            viCaNhan.rutTien(String.valueOf(moneyOld));
        }
        else {
            viCaNhan.napTien(String.valueOf(moneyOld));
        }
        return viCaNhan;
    }
}
