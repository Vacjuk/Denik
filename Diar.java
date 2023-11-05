/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.itnetwork.poznamkovac;

import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 *
 * @author Martin
 */
public class Diar {

    private Databaze databaze;
    private Scanner scanner = new Scanner(System.in, "Windows-1250");

    public Diar() {
        databaze = new Databaze();
    }

    private LocalDateTime naparsujDatumCas() {
        System.out.println("Zadejte datum a čas ve tvaru [" + LocalDateTime.now().format(FORMAT_DATA) + "]");
        while (true) {
            try {
                return LocalDateTime.parse(scanner.nextLine(), FORMAT_DATA);
            } catch (Exception ex) {
                System.out.println("Nesprávně zadáno, zadejte prosím znovu.");
            }
        }
    }

    private LocalDateTime naparsujDatum() {
        System.out.println("Zadejte datum ve tvaru [" + LocalDate.now().format(FORMAT_DATA_BEZ_CASU) + "]");
        while (true) {
            try {
                return LocalDate.parse(scanner.nextLine(), FORMAT_DATA_BEZ_CASU).atStartOfDay();
            } catch (Exception ex) {
                System.out.println("Nesprávně zadáno, zadejte prosím znovu.");
            }
        }
    }

    public void vypisZaznamy(LocalDateTime den) {
        ArrayList<Zaznam> zaznamy = databaze.najdiZaznamy(den, false);
        for (Zaznam zaznam : zaznamy) {
            System.out.println(zaznam);
        }
    }
    
    public void pridejZaznam() {
        LocalDateTime datumCas = naparsujDatumCas();
        System.out.println("Zadejte text zánamu:");
        String text = scanner.nextLine();
        databaze.pridejZaznam(datumCas, text);
    }
    
    public void vyhledejZaznamy() {
        // Zadání data uživatelem
        LocalDateTime datumCas = naparsujDatum();
        // Vyhledávání záznamů
        ArrayList<Zaznam> zaznamy = databaze.najdiZaznamy(datumCas, false);
        // Výpis záznamů
        if (zaznamy.size() > 0) {
            System.out.println("Nalezeny tyto záznamy: ");
            for (Zaznam zaznam : zaznamy) {
                System.out.println(zaznam);
            }
        } else {
            System.out.println("Nebyly nalezeny žádné záznamy.");
        }
    }
    
    public void vymazZaznamy() {
        System.out.println("Budou vymazány záznamy v daný den a hodinu");
        LocalDateTime datumCas = naparsujDatumCas();
        databaze.vymazZaznam(datumCas);
    }
    
    public void vypisUvodniObrazovku() {
        System.out.println();
        System.out.println();
        System.out.println("Vítejte v diáři!");
        LocalDateTime dnes = LocalDateTime.now();
        System.out.printf("Dnes je: %s%n", FORMAT_DATA.format(dnes));
        System.out.println();
        // výpis hlavní obrazovky
        System.out.println("Dnes:\n-----");
        vypisZaznamy(dnes);
        System.out.println();
        System.out.println("Zítra:\n-----");
        LocalDateTime zitra = LocalDateTime.now().plusDays(1);
        vypisZaznamy(zitra);
        System.out.println();
    }

    public static final DateTimeFormatter FORMAT_DATA = DateTimeFormatter.ofPattern("d.M.y H:mm");
    public static final DateTimeFormatter FORMAT_DATA_BEZ_CASU = DateTimeFormatter.ofPattern("d.M.y");

}
