function ScrollDiv(){

   if(document.getElementById('scroll1').scrollTop<(document.getElementById('scroll1').scrollHeight-document.getElementById('scroll1').offsetHeight)){-1
         document.getElementById('scroll1').scrollTop=document.getElementById('scroll1').scrollTop+1
         }
   else {document.getElementById('scroll1').scrollTop=0;}
}
setInterval(ScrollDiv,25)