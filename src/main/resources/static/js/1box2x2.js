function ScrollDiv(){

   if(document.getElementById('ecran1').scrollTop<(document.getElementById('ecran1').scrollHeight-document.getElementById('ecran1').offsetHeight)){-1
         document.getElementById('ecran1').scrollTop=document.getElementById('ecran1').scrollTop+1
         }
   else {document.getElementById('ecran1').scrollTop=0;}
}
setInterval(ScrollDiv,60)