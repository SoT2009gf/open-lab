function ScrollDiv(){

   if(document.getElementById('ecran2').scrollTop<(document.getElementById('ecran2').scrollHeight-document.getElementById('ecran2').offsetHeight)){-1
         document.getElementById('ecran2').scrollTop=document.getElementById('ecran2').scrollTop+1
         }
   else {document.getElementById('ecran2').scrollTop=0;}
}
setInterval(ScrollDiv,25)