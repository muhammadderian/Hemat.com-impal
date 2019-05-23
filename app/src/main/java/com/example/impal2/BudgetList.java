package com.example.impal2;

public class BudgetList {
    int id_bdgt,budget;
    String nameUsr,jenis,deskripsi,waktu;

    public BudgetList(int id_bdgt, int budget, String nameUsr, String jenis, String deskripsi, String waktu) {
        this.id_bdgt = id_bdgt;
        this.nameUsr = nameUsr;
        this.budget = budget;
        this.jenis = jenis;
        this.deskripsi = deskripsi;
        this.waktu = waktu;
    }

    public int getId_bdgt() {
        return id_bdgt;
    }

    public int getBudget() {
        return budget;
    }

    public String getNameUsr() {
        return nameUsr;
    }

    public String getJenis() {
        return jenis;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getWaktu() {
        return waktu;
    }
}
