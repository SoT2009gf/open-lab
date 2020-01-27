function ScrollDiv(){

   if(document.getElementById('ecran4').scrollTop<(document.getElementById('ecran4').scrollHeight-document.getElementById('ecran4').offsetHeight)){-1
         document.getElementById('ecran4').scrollTop=document.getElementById('ecran4').scrollTop+1
         }
   else {document.getElementById('ecran4').scrollTop=0;}
}
setInterval(ScrollDiv,60)