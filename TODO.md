# Clubspire
-********TODO********




- Toolbar není implementovaný úplně správně
* dodělat v HintActivity + onClickListener pro navigation Arrow + předělat správně odkazy pro stisknutí Toolbaru(MainMenu / oneStepBack)



- hromadí se vám v aplikaci activity, po pár průchodech je stack dost 
plný - neukončujete activitu při jejím opustění

*Implementováno řešení z:
http://stackoverflow.com/questions/4732184/how-to-finish-an-android-application
    funguje dobře, ale nerozumím, jak to funguje.
    při spuštení HintActivity to allocuje mnohonásobně více paměti a pak jí to neuvolní



- stejně tak tlačítko pro změnu týdne
týdny by se klidně daly přepína swipem nebo to udělejte jako 
number picker přes dialog

*DONE + jak efektivněji zpracovat swipe. Momentálně jde o TextView, reagující na swipe 
změnou textu, ale nijak graficky
    asi předělat na swipovacé fragment jako v HintActivity



- zkuste to graficky a UX více učesat, barevně viz Clubspire Webclient, 
Androidu komponenty nepřestylovávat, input pro poznámku více místa

*nevím, kde přesně vzít barevné hodnoty.
https://rezervace.partyfit.cz/public/Welcome.do  ?
hodily by se spíše konkrétní kódy pro primární a sekundární barvu

Implementačne:
- ListView, resp. adaptery by měly implementovat ViewHolder pattern viz 
http://developer.android.com/training/improving-layouts/smooth-scrolling.html 
nebo http://www.vogella.com/tutorials/AndroidListView/article.html (8.4)

*todo, zatím mi to nešlo. O co vlastně v tomto požadavku jde, o smooth scrolling?


- snažte se nepočítat velikosti komponent v kódu, pokud to není nezbytně 
nutné např. jaká je motivace pro nastavení výšky tlačítek v rezervaci?

*k šířce tlačítka: jak jinak se dá nastavit šířka tlačítka na poloviční šířku displeje?
při jiném nastavení dochází k překrývání tlačítek na menším displeji.
Pokud to je připomínka pouze k výšce, tak tu samozřejmě upravovat nemusíme


- try catch NullPointerException, to bych viděl spíš na if

*DONE*


- title activity lze nastavit v manifestu

*todo 


- nevidím důvod, proč by activity neměla mít extra, pokud je tam 
posíláte, rozhodně pokud se to stane a activity nemá potřebná data, pak 
musí umřít

*todo


- udělejte si vlastního abstraktního předka pro activity, dejte tam 
společnou logiku

*todo castecne implementovane AbstractReservationActivity. Bude sa dat implementovat aj dalsia dedicnost. 


- grafické assety by neměly být v drawable - zde jsou typicky generované 
assety pomocí XML nebo 9-patche

- PNG patří do drawable-XYZ, kde XYZ je identifikátor density zařízení, 
dle toho, v jakém rozlišení PNG je viz 
http://developer.android.com/guide/practices/screens_support.html

*jak pracovat se složkama drawable. když mám ikony uložené 
v podsložkách (hdpi/ldpi/...), nemohu k nim přistoupit


- mipmapy jsou užitečné až od Androidu 4.3, kompatibilitu máte od 4.0, 
takže je nepoužívejte

*ty jsme ani nikde nepoužívali. Složky jsem smazal

- zkuste mít layout co nejvíce plochý (málo zanořených ViewGroups v 
sobě), kde to jde (skoro vždy) používejte LinearLayout místo RelativeLayout

*je potřeba to měnit? Podstatně lépe se mi pracuje s Relative 


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
