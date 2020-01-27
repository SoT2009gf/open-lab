  
var animation1 = document.getElementsByClassName("column-1");
var animation2 = document.getElementsByClassName("column-2");
var animation3 = document.getElementsByClassName("column-3");
var barAnimation = document.getElementsByClassName("in");
const jobs1 = document.getElementsByClassName("loaded-jobs1");
const jobs2 = document.getElementsByClassName("loaded-jobs2");
const jobs3 = document.getElementsByClassName("loaded-jobs3");
const jobdesc1 = document.getElementsByClassName("jobdesc1");
const jobdesc2 = document.getElementsByClassName("jobdesc2");
const jobdesc3 = document.getElementsByClassName("jobdesc3");
const qrCodes1 = document.getElementsByClassName("qrcodes1");
const qrCodes2 = document.getElementsByClassName("qrcodes2");
const qrCodes3 = document.getElementsByClassName("qrcodes3");
const positions1 = document.getElementsByClassName("positions1");
const positions2 = document.getElementsByClassName("positions2");
const positions3 = document.getElementsByClassName("positions3");
 
var index = 0;
var job1;
var job2;
var job3;
var qrCode1;
var qrCode2;
var qrCode3;
var animation1clone;
var animation2clone;
var animation3clone;
var jobdesctemp1;
var jobdesctemp2;
var jobdesctemp3;
var postemp1;
var postemp2;
var postemp3;

slide();
setInterval(slide, 5000);

function slide() {
	if (job1) {
		job1.classList.remove("displayed");
		job1.classList.add("hidden");
		jobdesctemp1.classList.remove("displayed");
		jobdesctemp1.classList.add("hidden");
		qrCode1.classList.remove("displayed");
		qrCode1.classList.add("hidden");
		postemp1.classList.remove("displayed");
		postemp1.classList.add("hidden");

	}

	if (job2) {
		job2.classList.remove("displayed");
		job2.classList.add("hidden");
		jobdesctemp2.classList.remove("displayed");
		jobdesctemp2.classList.add("hidden");
		qrCode2.classList.remove("displayed");
		qrCode2.classList.add("hidden");
		postemp2.classList.remove("displayed");
		postemp2.classList.add("hidden");
	}

	if (job3) {
		job3.classList.remove("displayed");
		job3.classList.add("hidden");
		jobdesctemp3.classList.remove("displayed");
		jobdesctemp3.classList.add("hidden");
		qrCode3.classList.remove("displayed");
		qrCode3.classList.add("hidden");
		postemp3.classList.remove("displayed");
		postemp3.classList.add("hidden");
	}

	animation1clone = animation1[0].cloneNode(true);
	animation1[0].parentNode.replaceChild(animation1clone, animation1[0]);
	animation2clone = animation2[0].cloneNode(true);
	animation2[0].parentNode.replaceChild(animation2clone, animation2[0]);
	animation3clone = animation3[0].cloneNode(true);
	animation3[0].parentNode.replaceChild(animation3clone, animation3[0]);
	barAnimClone = barAnimation[0].cloneNode(true);
	barAnimation[0].parentNode.replaceChild(barAnimClone, barAnimation[0]);
	animation1[0] = animation1clone;
	animation2[0] = animation2clone;
	animation3[0] = animation3clone;
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
	positions1[index].classList.remove("hidden");
	positions1[index].classList.add("displayed");

	job1 = jobs1[index];
	qrCode1 = qrCodes1[index];
	jobdesctemp1 = jobdesc1[index];
	postemp1 = positions1[index];

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
	positions2[index].classList.remove("hidden");
	positions2[index].classList.add("displayed");

	job2 = jobs2[index];
	qrCode2 = qrCodes2[index];
	jobdesctemp2 = jobdesc2[index];
	postemp2 = positions2[index];

	index += 1;

	if (index > (jobs1.length - 1)) {
		index = 0;
	}

	jobs3[index].classList.remove("hidden");
	jobs3[index].classList.add("displayed");
	jobdesc3[index].classList.remove("hidden");
	jobdesc3[index].classList.add("displayed");
	qrCodes3[index].classList.remove("hidden");
	qrCodes3[index].classList.add("displayed");
	positions3[index].classList.remove("hidden");
	positions3[index].classList.add("displayed");

	job3 = jobs3[index];
	qrCode3 = qrCodes3[index];
	jobdesctemp3 = jobdesc3[index];
	postemp3 = positions3[index];

	index += 1;
}