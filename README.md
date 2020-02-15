# Sherif-of-Nottingham
Java implementation of the real game

 Jucatori:
 - clasa abstracta MyCharacter ce contine modurile de joc ( serif si comerciant)
   ce sunt suprascrise de fiecare tipologie in parte si structurile de date 
   corespunzatoare pentru bunurile din mana, cele din sac si de pe taraba.Metodele
   suprascrise pentru fiecare strategie: checkgisgoods ( in care seriful verifica
   bunurile din sacul fiecarui jucator in parte eliminand pe cele ce nu corespund 
   cu declarati sau sunt ilegale) , organizebag ( in care fiecare jucator isi 
   pune bunurile din sac pe taraba ce sunt retinute in hashmapul totalgoods, 
   drawgoods ( este utilizata pentru impartirea celor 10 carti pe subrunde).
	  
 - clasa BasicMyCharacter ce extinde clasa MyCharacter si este mostenita de 
   celelalte strategii.(ex: greedy joaca identic cu basic pe rundele impare 
   ca si comerciant si ca serif adauga mita).
 
 - clasa GreedyMyCharacter ce extinde Basic ce are ca si membru specific numarul
   de runde ce este updatat la fiecare runda in Game.
 
 - clasa BribeMyCharacter ce extinde Basic ce are ca si membrii playerul din
   stanga si dreapta sa.( deoarece stau la o masa circulara ).Pentru rolul de 
   comerciant retin 2 vectori pentru cartile legale si cele ilegale sortati
   dupa profit. Daca jucatorul are o suma <= 5 acesta va adopta strategia lui basic 
   apeland direct metodele respective. In functie de numarul de bunuri ilegale din
   mana se seteaza mita iar numarul de carti pe care le ia jucatorul o sa fie 
   Math.min(maxilegal, bribedhand.size()) - maxilegal este numarul de carti ilegale
   maxim pentru suma de bani detinuta de jucator.Analog se procedeaza si pentru
   bunurile legale.

 Mecanica de joc:
 - in clasa GameLogic instantiez vectorul de jucatori in functie de fiecare
   strategie
 - parcurg Arraylist respectiv si la fiecare noua runda pentru jucatorii cu
   strategia de tip Greedy incrementez numarul de runde(pentru a tine cont de
   paritate).
 - la fiecare noua subrunda este ales un nou serif ce controleaza toti jucatorii in 
   parte cu metoda suprascrisa din clasa parinte MyCharacter.
 - metodele auxiliare computebasic() si computebonuses() utilizate pentru calcularea
   sumelor finale.
 - in computebonuses() utilizez cele doua matrici leaderboard( 1 pentru king si 2
   pentru queen leaderboard[i][j] = numarul maxim de obiecte de id i si pentru 
   j=0(numarul de carti de tip i si pebtru j = 1 id-ul jucatorului ce detine 
   titlul curent.
 - pachetul de carti este stocat in variabila gameGoods ce este data ca parametru in
   situatiile ce duc la modificarea pachetului( se trag cartile si se confisca cele
   ilegale.
   
   In main dupa citire si apelarea metodelor pentru sumele de bani dupa terminarea 
   jocului se afiseaza sumele finale.      
  
