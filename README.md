# BookBoard

Dette er en applikasjon for å booke studentassistenter, for å gjøre det lettere for studenter å vite når det er mulighet for å få hjelp og få en oversiktlig tilstrømning av studenter på sal.
Applikasjonen skal også inneholde tilrettelegging for bedre kommunikasjon og levering av øvinger.

## Build status



## Standarder for koding

Vi har valgt å følge standarder beskrevet i dette dokumentet: https://google.github.io/styleguide/javaguide.html

## Teknologi

* Prosjektet er kodet i java, og vi har brukt IntelliJ IDEA som programmeringsomgivelse. 
* Vi har brukt JavaFx Scenebuilder for å designe produktet. 
* MySQL er brukt som databaseverktøy.
* Java 11 er brukt som SDK
* Maven har blitt brukt som rammeverk for å bygge prosjektet, kjøre tester og deploye til kjørbar .jar fil.

## Funksjonalitet

BookBoard har følgende funksjonalitet:
* Admin bruker med mulighet for å opprette brukere, emner og legge til/endre roller mellom brukere og emner.
* Faglærer som kan legge til saltider for sine emner.
* Studasser som kan legge til tider når de er ledige innenfor gitte saltider i sine emner.
* Studenter som kan booke ledige studasser når de vil møte opp på sal.
* En bruker kan være meldt opp i ulike emner med ulike roller
* Brukerene har mulighet til å kommunisere seg imellom ved meldinger, og får opp varsler på uleste meldinger.
* Faglærer kan sende ut kunngjøringer for spesifikke emner, til alle/studenter/studasser.
* Mulighet for et øvingssystem der faglærer kan opprette øvinger, studenter kan levere og studasser og faglærer kan rette.

## Screenshots

![admin](https://i.imgur.com/2lfmsDy.png)
![emne](https://i.imgur.com/0lCzvde.png)
![student](https://i.imgur.com/anq62YH.png)
![meldinger](https://i.imgur.com/Cnzhno0.png)
![innlevering](https://i.imgur.com/o3BemqG.png)

## Installasjon

1. Clone reposetorien ved å skrive git clone git@gitlab.stud.idi.ntnu.no:programvareutvikling-v19/gruppe-16.git i terminalen i ønsket mappe.
2. Åpne prosjektet i ønsket IDEA, for eksempel IntelliJ IDEA.
3. Bruk sql-tabellene som ligger i sql/tabeller.sql, og sett opp egen database.

## Tester

Testene til prosjektet ligger i filen MainTest.java som ligger i src/test/java/MainTest.java.

Testene blir også kjørt om man laster inn prosjektet som et Maven prosjekt, og så kjører Maven test kommandoen.

