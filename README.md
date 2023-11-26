Необходимо скачать Apache Tomcat 10.1.13, в среде разработки выбрать edit configurations и выбрать tomcat. Во вкладке Deployment параметр Application context оставить пустым. 
В файле PersonDAO в методе connect() в строчке 29 укажите ваш абсолютный путь к БД, лежащей в папке webapp/resources(Просим прощения за такие неудобства).
