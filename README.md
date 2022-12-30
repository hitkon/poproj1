# poproj1
proj1, var 2211 (Piekielny portal, toksyczne trupy, pełna losowość, pełna predestynacja)

(+) 1. Aplikacja ma być realizowana z użyciem graficznego interfejsu użytkownika z wykorzystaniem biblioteki JavaFX.
(+) 2. Jej głównym zadaniem jest umożliwienie uruchamiania symulacji o wybranych konfiguracjach.
  Powinna umożliwić wybranie jednej z uprzednio przygotowanych gotowych konfiguracji.
  Dostępne konfiguracje mają formę znajdujących się w odpowiednim folderze na dysku plików konfiguracyjnych (plik może zawierać po prostu pary klucz + wartość, po jednej   na linijkę).
  Istnieje możliwość wczytania alternatywnej, sporządzonej przez użytkownika konfiguracji.
(+) 3. Uruchomienie symulacji powinno skutkować pojawieniem się nowego okna obsługującego daną symulację.
  Jednocześnie uruchomionych może być wiele symulacji, każda w swoim oknie, każda na osobnej mapie.
(+) 4. Sekcja symulacji ma wyświetlać animację pokazującą pozycje zwierząt, ich energię w dowolnej formie (np. koloru lub paska zdrowia) oraz pozycje roślin - i ich zmiany.
(+) 5. Program musi umożliwiać zatrzymywanie oraz wznawianie animacji w dowolnym momencie (niezależnie dla każdej mapy - patrz niżej).
(+) 6. Program ma pozwalać na śledzenie następujących statystyk dla aktualnej sytuacji w symulacji:
  liczby wszystkich zwierząt,
  liczby wszystkich roślin,
  liczby wolnych pól,
  najpopularniejszych genotypów,
  średniego poziomu energii dla żyjących zwierząt,
  średniej długości życia zwierząt dla martwych zwierząt (wartość uwzględnia wszystkie nieżyjące zwierzęta - od początku symulacji),
(+) 7. Jeżeli zdecydowano się na to w momencie uruchamiania symulacji, to jej statystyki powinny być zapisywane (każdego dnia) do pliku CSV. Plik ten powinnien być "otwieralny" przez dowolny rozujmiejący ten format program (np. MS Excel).
(-) 8. Po zatrzymaniu programu można oznaczyć jedno zwierzę jako wybrane do śledzenia. Od tego momentu (do zatrzymania śledzenia) UI powinien przekazywać nam informacje o   jego statusie i historii:
  jaki ma genom,
  która jego część jest aktywowana,
  ile ma energii,
  ile zjadł roślin,
  ile posiada dzieci,
  ile dni już żyje (jeżeli żyje),
  którego dnia zmarło (jeżeli żywot już skończyło).
(-)  9. Po zatrzymaniu programu powinno być też możliwe pokazanie, które ze zwierząt mają dominujący (najpopularniejszy) genotyp (np. poprzez wyróżnienie ich wizualnie).
(+) 10. Aplikacja powinna być możliwa do zbudowania i uruchomienia z wykorzystaniem Gradle'a.
