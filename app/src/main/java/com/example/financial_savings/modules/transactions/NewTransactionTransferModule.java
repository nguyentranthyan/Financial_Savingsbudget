package com.example.financial_savings.modules.transactions;

import android.widget.CheckBox;

import com.example.financial_savings.entities.DanhMuc;
import com.example.financial_savings.entities.SoGiaoDich;
import com.example.financial_savings.entities.SuKien;
import com.example.financial_savings.entities.TietKiem;
import com.example.financial_savings.entities.ViCaNhan;
import com.example.financial_savings.helper.DBHelper;
import com.example.financial_savings.modules.others.AccountCurrentModule;
import com.example.financial_savings.templates.transactions.add_transaction.EventSaveTemplate;

public class NewTransactionTransferModule {

    public static SoGiaoDich setNewSoGiaoDich(DBHelper dbHelper, String money, String note, java.sql.Date sqlDate,
                                               DanhMuc danhMuc, ViCaNhan viCaNhan, TietKiem tietKiem,
                                               SuKien suKien, CheckBox checkBox, String remind, String repeat) {
        SoGiaoDich soGiaoDich = new SoGiaoDich();
        soGiaoDich.setSoTien(Double.parseDouble(money));
        soGiaoDich.setGhiChu(note);
        soGiaoDich.setNgayGiaoDich(sqlDate);
        soGiaoDich.setMasv(AccountCurrentModule.getSinhVienCurrent(dbHelper).getMasv());
        soGiaoDich.setMaDanhMuc(danhMuc.getMaDanhMuc());
        soGiaoDich.setLapLai(repeat);
        EventSaveTemplate.handlingRemind(remind, soGiaoDich);
        EventSaveTemplate.handlingSetProperty(soGiaoDich, viCaNhan, tietKiem, suKien);
        EventSaveTemplate.handlingSetStatus(soGiaoDich, checkBox);
        EventSaveTemplate.handlingCheckStatus(dbHelper, soGiaoDich);
        return soGiaoDich;
    }
}
