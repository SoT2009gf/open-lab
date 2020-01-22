function ScrollDiv(){

   if(document.getElementById('scroll').scrollTop<(document.getElementById('scroll').scrollHeight-document.getElementById('scroll').offsetHeight)){-1
         document.getElementById('scroll').scrollTop=document.getElementById('scroll').scrollTop+1
         }
   else {document.getElementById('scroll').scrollTop=0;}
}
setInterval(ScrollDiv,25)