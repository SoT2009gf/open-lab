var animation1 = document.getElementsByClassName("row1");
var animation2 = document.getElementsByClassName("row2");
var barAnimation = document.getElementsByClassName("in");
const jobs1 = document.getElementsByClassName("loaded-jobs1");
const jobs2 = document.getElementsByClassName("loaded-jobs2");
const jobdesc1 = document.getElementsByClassName("jobdesc1");
const jobdesc2 = document.getElementsByClassName("jobdesc2");
const qrCodes1 = document.getElementsByClassName("qr-codes1");
const qrCodes2 = document.getElementsByClassName("qr-codes2");

var index = 0;
var job1;
var job2;
var qrCode1;
var qrCode2;
var animation1clone;
var animation2clone;
var barAnimClone;
var jobdesctemp1;
var jobdesctemp2;

slide();
setInterval(slide, 10000);

function slide() {
	if (job1) {
		job1.classList.remove("displayed");
		job1.classList.add("hidden");
		jobdesctemp1.classList.remove("displayed");
		jobdesctemp1.classList.add("hidden");
		qrCode1.classList.remove("displayed");
		qrCode1.classList.add("hidden");
	}

	if (job2) {
		job2.classList.remove("displayed");
		job2.classList.add("hidden");
		jobdesctemp2.classList.remove("displayed");
		jobdesctemp2.classList.add("hidden");
		qrCode2.classList.remove("displayed");
		qrCode2.classList.add("hidden");
	}

	animation1clone = animation1[0].cloneNode(true);
	animation1[0].parentNode.replaceChild(animation1clone, animation1[0]);
	animation2clone = animation2[0].cloneNode(true);
	animation2[0].parentNode.replaceChild(animation2clone, animation2[0]);
	barAnimClone = barAnimation[0].cloneNode(true);
	barAnimation[0].parentNode.replaceChild(barAnimClone, barAnimation[0]);
	animation1[0] = animation1clone;
	animation2[0] = animation2clone;
	barAnimation[0] = barAnimClone;	

	if (index > (jobs1.length - 1)) {
		index = 0;
	}

	jobs1[index].classList.remove("hidden");
	jobs1[index].classList.add("displayed");
	jobdesc1[index].classList.remove("hidden");
	jobdesc1[index].classList.add("displayed");
	qrCodes1[index].classList.remove("hidden");
	qrCodes1[index].classList.add("displayed");

	job1 = jobs1[index];
	qrCode1 = qrCodes1[index];
	jobdesctemp1 = jobdesc1[index];

	index += 1;

	if (index > (jobs1.length - 1)) {
		index = 0;
	}

	jobs2[index].classList.remove("hidden");
	jobs2[index].classList.add("displayed");
	jobdesc2[index].classList.remove("hidden");
	jobdesc2[index].classList.add("displayed");
	qrCodes2[index].classList.remove("hidden");
	qrCodes2[index].classList.add("displayed");

	job2 = jobs2[index];
	qrCode2 = qrCodes2[index];
	jobdesctemp2 = jobdesc2[index];

	index += 1;
}