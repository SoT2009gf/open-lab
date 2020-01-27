function ScrollDiv(){

   if(document.getElementById('ecran5').scrollTop<(document.getElementById('ecran5').scrollHeight-document.getElementById('ecran5').offsetHeight)){-1
         document.getElementById('ecran5').scrollTop=document.getElementById('ecran5').scrollTop+1
         }
   else {document.getElementById('ecran5').scrollTop=0;}
}
setInterval(ScrollDiv,60)