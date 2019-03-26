# BookBoard

Dette er en applikasjon for å booke studentassistenter, for å gjøre det lettere for studenter å vite når det er mulighet for å få hjelp og få en oversiktlig tilstrømning av studenter på sal.
Applikasjonen skal også inneholde tilrettelegging for bedre kommunikasjon og levering av øvinger.

## Build status

![badge](https://badge.buildkite.com/sample.svg?status=passing)

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

## Fremtidig funksjonalitet


## Screenshots
Admin side med mulighet for å oppretting og endring.
![admin](https://i.imgur.com/2lfmsDy.png)

Hjemmeside for bruker med oversikt over emner og bookinger.
![emne](https://i.imgur.com/0lCzvde.png)

Det studenten ser når han skal booke en tid.
![student](https://i.imgur.com/anq62YH.png)

Kommunisering mellom brukere med meldingsystemet
![meldinger](https://i.imgur.com/Cnzhno0.png)

Det studenten ser når han skal levere inn enn øving.
![innlevering](https://i.imgur.com/o3BemqG.png)

## Installasjon

1. Clone reposetorien ved å skrive git clone git@gitlab.stud.idi.ntnu.no:programvareutvikling-v19/gruppe-16.git i terminalen i ønsket mappe.
2. Åpne prosjektet i ønsket IDEA, for eksempel IntelliJ IDEA. Og last det inn som et Maven prosjekt.
3. Om man har tilgang til NTNU sitt nettverk kan man bruke databasen som er koblet opp.
4. Viss ikke kan man kjøre sql scriptet (som ligger i sql/tabeller.sql) og sette opp egen database og koble seg opp mot denne ved å endre feltene i DatabaseControlleren.
5. Nå er det bare å begynne å lese seg litt opp på de forskjellige metodene og starte å utvikle nye features.

## Tester

* Testene til prosjektet finner man i src/test/java/MainTest.java, og kan kjøres direkte derfra.
* Testene blir også kjørt om man laster inn prosjektet som et Maven prosjekt, og så kjører Maven test kommandoen.

## Bidrag

Agnes Marie Ockernahl
Ragnhild Kleiven
Bjørn Haugland Spangelo
Ingebrigt Nygård


