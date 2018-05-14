package com.company;

import com.company.manager.ManagerCorredors;
import com.company.manager.ManagerCorredors2;
import com.company.manager.ManagerEquips;
import com.company.model.Equip;
import com.company.view.PantallaMenuPrincipal;

public class Main2 {

    public static void main(String[] args) {
        Equip equip = new Equip("equipoA");
        new ManagerCorredors2().inscriureCorredor("pepe",equip);


    }
}
