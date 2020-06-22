package com.example.financial_savings.modules.checks;

import com.example.financial_savings.entities.SuKien;
import com.example.financial_savings.entities.TietKiem;
import com.example.financial_savings.entities.ViCaNhan;
import com.example.financial_savings.helper.DBHelper;

public class CheckPropertyRepeatModule {

    public static ViCaNhan checkWallet(String id, DBHelper dbHelper) {
        try {
            return dbHelper.getByID_ViCaNhan(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static TietKiem checkSavings(String id, DBHelper dbHelper) {
        try {
            return dbHelper.getByID_TietKiem(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static SuKien checkEvent(String id, DBHelper dbHelper) {
        try {
            return dbHelper.getByID_SuKien(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
