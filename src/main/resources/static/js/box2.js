function ScrollDiv(){

   if(document.getElementById('ecran3').scrollTop<(document.getElementById('ecran3').scrollHeight-document.getElementById('ecran3').offsetHeight)){-1
         document.getElementById('ecran3').scrollTop=document.getElementById('ecran3').scrollTop+1
         }
   else {document.getElementById('ecran3').scrollTop=0;}
}
setInterval(ScrollDiv,60)