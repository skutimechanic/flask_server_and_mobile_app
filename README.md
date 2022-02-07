# Aplikacja mobilna "Movies Search"

## Część serwerowa aplikacji 

### Narzędzia konieczne do uruchomienia aplikacji
- Python
- Visual Studio Code (lub inne idea)
- MySql Server
- biblioteki zamieszczone w punkcie poniżej

### Wirutalne środowisko
Utworzenie wirtualnego środowiska: python -m venv venv
Uruchomienie z konsoli wirtualnego środowiska: w folderze server odpalić konsole i wpisac komende venv\Scripts\activate.bat
W wirtualnym środowisku zainstalować: 
- pip install flask
- pip install python-dotenv
- pip install flask-sqlalchemy
- pip install pymysql
- pip install cryptography
- pip install flask-migrate
- pip install marshmallow
- pip install webargs
- pip install pyjwt
Uruchomienie wirtualnego środowiska w vs code: https://stackoverflow.com/questions/54106071/how-can-i-set-up-a-virtual-environment-for-python-in-visual-studio-code

### Instalacja i uruchomienie MySql
MySql Community Server: https://dev.mysql.com/downloads/windows/installer/8.0.html, pobrać większy plik, w instalatorze wybrac "Developer defualt".
Ręczne uruchomienie servera mysql jest mozliwe za pomocą cmd: services.msc, następnie z listy wybrać MySql80 i uruchom.
W MySql 8.0 Command Line Client: "create database movie_library".

### Konfiguracja serwera
Dodać plik .env w folderze server o zawartości:
SECRET_KEY=SomeRandomString
SQLALCHEMY_DATABASE_URI=mysql+pymysql://<db_name>:<us_password>@localhost/movie_library?charset=utf8mb4

### Pierwsze uruchomienie
W folderze server będąc w wirtualnym środowisku, aby utworzyć tabele i nastepnie wypelnic je danymi:
- flask db upgrade
- flask db-manage add-data
- flask run
W celu usunięcia danych: flask db-manage remove-data

### Kolejne uruchomienia
W folderze server będąc w wirtualnym środowisku:
- flask run

### Dokumentacja endpointów
Dokumentacja endpointów wygenerowana z aplikacji Postman, dostępna jest pod linkiem: https://documenter.getpostman.com/view/19180188/UVeJKQLo


## Aplikacja mobilna 

### Narzędzia konieczne do uruchomienia aplikacji
- Android Studio min. ver. Arctic Fox | 2020.3.1
- Android sdk min. API level  28

### Uruchomienie projektu
- Otwrcie projektu w Android Studio, lokalizacja: \flask_server_and_mobile_app\client\app
- Synchronizacja projektu z plikami gradle
- Kompilacja projektu na emulatorze dostarczonym przez AS

### Obsługa aplikacji mobilnej
Obsługa aplikacji sprowadza się do nawigacji pomiędzy 3 tabami widocznymi na panelu nawigacyjnym u dołu ekranu.

Widok głównej listy w aplikacji - 1. tab:

![image](https://user-images.githubusercontent.com/73020115/152863994-da3bb54b-068a-4f13-a01c-c6b6801b17a7.png)

Widok listy filmów użytkownika w aplikacji - 2. tab.
Użytkownik niezalogowany: 

![image](https://user-images.githubusercontent.com/73020115/152864780-5788623b-dd8e-4219-bac1-99913a55ca64.png)

Użytkownik zalogowany: 

![image](https://user-images.githubusercontent.com/73020115/152864953-3c24f723-324e-406b-9ba0-bde06423a808.png)

Logowanie użytkownika:

![image](https://user-images.githubusercontent.com/73020115/152864858-7b1ee3a4-340e-4532-b041-2243b47fe7ec.png)

Możliwość wylogowywania - 3. tab:

![image](https://user-images.githubusercontent.com/73020115/152865937-73fe9fb4-a575-486b-a6d7-70bf21b97b1a.png)

Widok szczegółów filmów (użytkownik niezalogowany):

![image](https://user-images.githubusercontent.com/73020115/152867141-4c0c3ba3-6b46-4faa-99a5-5ecc30b08aa1.png)

Widok szczegółów filmów (użytkownik zalogowany):

![image](https://user-images.githubusercontent.com/73020115/152865699-bbb1af56-e042-4957-bada-c29ce19b15d4.png)

Istnieje możliwość dodawania filmów do listy użytkownika, oceniania filmów oraz usuwania ich ze swojej listy.
Dodawać filmy do swojej listy można z poziomu szczegółów filmu lub z poziomu głównej listy za pomocą przeciągnięcia pozycji:

![image](https://user-images.githubusercontent.com/73020115/152866305-4a1453d0-a40b-4b55-beac-f180070ca420.png)

Analogicznie opcja usuwania filmów dostępna jest z poziomu listy filmów użytkownika za pomocą gestu przeciągnięcia:

![image](https://user-images.githubusercontent.com/73020115/152866501-e780a218-ec60-4aa2-9a2f-6e8ef69bb0b8.png)

Stan ocen i bazy filmów użytkownika jest na bieżąco aktualizowany przez część serwerową i prezentowany użytkownikowi w 2. tabie aplikacji.

W systemie istnije również rola administratora, który ma możliwość globalnego usuwania filmów z systemu. Akcja ta jest możliwa do wykonania na szczegółach filmu po kliknięciu czerwonej ikony kosza: 

![image](https://user-images.githubusercontent.com/73020115/152867005-44bc649c-21ae-4ef2-9f1e-c00c9e66bf00.png)





