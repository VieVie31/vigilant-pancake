/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* 
 * ce fichier a ete trop chaint a faire donc le premier qui le kritik
 * je lui kritik sa gueule !!!!
 */

//make sure all previous notif diapear
setInterval(function() {
    var t = document.getElementsByTagName("notif");

    for (i = 0; i < t.length; i++)
        t[i].classList.add("fadeout");
}, 1000);


function notify(text) {
    var notif = document.createElement("notif");
    notif.classList.add("notification");
    notif.innerHTML = text;

    document.body.appendChild(notif);
}