# Clubspire
-********TODO********
********************************24.5.2015 Adam**************************************************
zobrazení názvu aplikace v menu

Toolbar text color - černá na mobilu (pouze u Michala:D)

TextField např u registrace bílá barva textu (po označení), předělat na HINT
DONE

navigation arrow fix (funguje, ale zeptat se na efektivnější řešení)

správa rexervace: zakazat editaci, tlačítko Potvrdit = Zpět, zrušit hlášku, šedé, + potvrzení u zrušení
DONE

Nápověda: progress..maybe

Toolbar onClick šedá
DONE

********************************22.5.2015 Adam**************************************************

- tlačítka mají tmavý text písma a pozadí - špatný kontrast, nečitelné
DONE

- input pro heslo má jiný font než input pro uživatelské jméno
Jaký font použít..všude? font se tam přenastavil automaticky

- ikona pro Up navigaci nejde téměř vidět na tmavém pozadí
DONE

- při tapnutí na aktivitu centra se zobrazí výchozí oranžové pozadí
TODO...nějak se tomu nechce

- text volného termínu je nečitelný - světlý text na světlém pozadí
DONE

- labely prvků v rezervačním formuláři jsou nečitelné - světlý text na světlém pozadí
DONE

- tapnutí na button v ActionBaru má výchozí oranžové pozadí
TODO

- inputy pro přihlášení/registraci mají při focusu výchozí oranžový border
DONE

- je mi divné, že všechny Android widgety (inputy, checboxy atd.) jsou z verze 2.x přitom to kompilujte oproti 4.x, zkuste zjistit proč, mělo by to vypadat viz http://developer.android.com/design/building-blocks/index.html

Funkční připomínky:
- nezobrazujte termín, který je obsazený, nebo ať na něj nejde tapnout 
(uživatel z červeného pozadí chápe, že tento termín je obsazený)
nelze na něj kliknout (neodkazuje do další aktivity)

- při přihlášení jde vidět lag, upozorněte uživatele progress barem,
 že se něco děje (obdobně při dalších operacích s API)- v nápovědě 
 jako uživatel nevím, že to má nějké další stránky, chybí zde nějaký viewpager indicator
 
- v nápovědě máte staré obrázky
TODO

********************************22.5.2015 Adam**************************************************



- Toolbar není implementovaný úplně správně
* DONE



- hromadí se vám v aplikaci activity, po pár průchodech je stack dost 
plný - neukončujete activitu při jejím opustění

*Implementováno řešení z:
http://stackoverflow.com/questions/4732184/how-to-finish-an-android-application
    funguje dobře, ale nerozumím, jak to funguje.
    při spuštení HintActivity to allocuje mnohonásobně více paměti a pak jí to neuvolní
*TODO: ciastocne poriesene, zabijanie procesu pri back arrow (pozri AbstractReservationActivity.toolbarListener()), nefunguje ale pri back kliku inde ako na sipku


- stejně tak tlačítko pro změnu týdne
týdny by se klidně daly přepína swipem nebo to udělejte jako 
number picker přes dialog

*DONE
implementovane spinnerom - snad dostatocne pohodlne


- zkuste to graficky a UX více učesat, barevně viz Clubspire Webclient, 
Androidu komponenty nepřestylovávat, input pro poznámku více místa

*ASK: nevím, kde přesně vzít barevné hodnoty.
https://rezervace.partyfit.cz/public/Welcome.do  ?
hodily by se spíše konkrétní kódy pro primární a sekundární barvu

Implementačne:
- ListView, resp. adaptery by měly implementovat ViewHolder pattern viz
http://developer.android.com/training/improving-layouts/smooth-scrolling.html 
nebo http://www.vogella.com/tutorials/AndroidListView/article.html (8.4)

*DONE pre ListReservationActivity.MyListAdapter - treba to robit aj inde? To bude asi jediny adapter requestujuci REST API

- snažte se nepočítat velikosti komponent v kódu, pokud to není nezbytně 
nutné např. jaká je motivace pro nastavení výšky tlačítek v rezervaci?

*DONE: k šířce tlačítka: jak jinak se dá nastavit šířka tlačítka na poloviční šířku displeje?
při jiném nastavení dochází k překrývání tlačítek na menším displeji.
Pokud to je připomínka pouze k výšce, tak tu samozřejmě upravovat nemusíme


- try catch NullPointerException, to bych viděl spíš na if

*DONE*


- title activity lze nastavit v manifestu

*DONE


- nevidím důvod, proč by activity neměla mít extra, pokud je tam 
posíláte, rozhodně pokud se to stane a activity nemá potřebná data, pak 
musí umřít

*DONE (?)


- udělejte si vlastního abstraktního předka pro activity, dejte tam 
společnou logiku

*DONE


- grafické assety by neměly být v drawable - zde jsou typicky generované 
assety pomocí XML nebo 9-patche

- PNG patří do drawable-XYZ, kde XYZ je identifikátor density zařízení, 
dle toho, v jakém rozlišení PNG je viz 
http://developer.android.com/guide/practices/screens_support.html

*jak pracovat se složkama drawable. když mám ikony uložené 
v podsložkách (hdpi/ldpi/...), nemohu k nim přistoupit


- mipmapy jsou užitečné až od Androidu 4.3, kompatibilitu máte od 4.0, 
takže je nepoužívejte

*DONE ty jsme ani nikde nepoužívali. Složky jsem smazal

- zkuste mít layout co nejvíce plochý (málo zanořených ViewGroups v 
sobě), kde to jde (skoro vždy) používejte LinearLayout místo RelativeLayout

*DONE je potřeba to měnit? Podstatně lépe se mi pracuje s Relative 


- pozadí termínu můžete ovlivnit taky pomocí 
http://developer.android.com/reference/android/graphics/drawable/StateListDrawable.html 
a stavu jednotlivých řádků (enabled)

*ještě se na to podívám

**********DONE**********************

-v textových polích nastavit, aby se při označení předvyplněný
text smazal a nahradil textem od uživatele
*DONE

- chybí mi up lepší up navigace - dejte tam tu šipku v levé části
nebo alespoň logo
*DONE


- kliknutí na menu v aktivitě menu otevírá menu

*DONE*

- přihlášení jde obejít kliknutím do Toolbaru

*DONE

- výpis aktivit, resp. řádek s activitou je moc uzký, nedá se do toho trefit

*DONE*


- obecně více vzdušnější, ať se lépe ovládá (zkuste si v tom klikat, když
pojedete v šalině - dokončite pohodlně rezervaci?)

*DONE*

- na obsazený týden lze klinout a udělat v něm rezervaci

*DONE*

- výpis informací o zvoleném termínu přes ListView není moc korektní,
jde na to klikat a přitom se nic nestane

*DONE*

- není řešeno odhlášení, nebo jsem jej nenašel

*DONE* v toolbaru

- pro nápovědu zkuste použít ViewPager

*DONE

- adaptery nedávejte do inner classes, vytáhněte ven, ať se dá použít i
jinde

*DONE*
