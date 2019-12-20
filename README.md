# Projet_Mobile_4A
## Fait par
MOUHAMMADOUL AMINE Abdallah
(4A initial)

## Présentation

Projet démontrant l'utilisation de Clean Architecture et du pattern MVVM dans une application android codé en Java.

Cette application pemet de trouver des mangas, voir les détails les concernant, et lire les chapitres de ces mangas.
Une section permet aussi de voir les chapitres de mangas sortis récemment (dans les 2 dernières semaines) et ainsi les lire.


## Consignes respectées :
-
	- Utilisation de fragments
	- Appel WebService à une API Rest. (API utilisée : https://www.mangaeden.com/api/ )
	- Stockage des données en cache.
  - Design
  - Gitflow
  - Bottom Navigation

- Fonctions supplémentaires :
	- Architecture MVVM
  - Architecture Components (Livedata, ViewModel, Room + SQLite pour stocker les données)
  - RxJava
  - Fonctionnalités de l'appli (searchview, filtres, lecture)

## Fonctionnalités: 

### Ecran Home 

- Affiche la liste de tous les mangas (limité à 1000)

<img src="readme_images/allMangas.jpg" width="268" height="467" alt="all mangas">

### Ecran du détail du manga
Affiche : l'image, le nom de l'auteur, la description du manga, liste des catégories dans lesquelle se place le manga
et enfin une liste des 100 (ou moins) derniers chapitres du manga

<img src="readme_images/mangaDetails1.jpg" width="268" height="467" alt="mangaDetails1">                   <img src="readme_images/mangaDetails2.jpg" width="268" height="467" alt="mangaDetails2">


En appuyant sur un chapitre, un nouveau fragment s'ouvre, permettant de lire chapitre (gallerie d'images)


<img src="readme_images/mangaChapter1.jpg" width="268" height="467" alt="mangaChapter1">                   <img src="readme_images/mangaChapter2.jpg" width="268" height="467" alt="mangaChapter1">                   

### Filtres
Filtre par nom de manga

<img src="readme_images/mangasFilterdByName.jpg" width="268" height="467" alt="mangasFilterdByName">

Filtre par genres (par exemple ici ne voir que les mangas de sport)

<img src="readme_images/categoriesFilter.jpg" width="268" height="467" alt="categoriesFilter">                <img src="readme_images/mangasFilteredByCategory.jpg" width="268" height="467" alt="mangasFilteredByCategory">

### Chapitres Récents
Réutilisation du fragments d'affichage de liste de chapitres mais cette fois ci pour afficher,
quelque soit le manga, son (ses) chapitre(s) sorti(s) récemment s'il y en a.

<img src="readme_images/recentChapters.jpg" width="268" height="467" alt="recentChapters">


## Appris

- Programmation réactive (découverte de RxJava)
- Utilisation de base de données sqlite pour une application android (Room, DAO, entity, database...)
- Utilisation de Livedata
- Utilisation de fragments
