const jobtitles1 = document.getElementsByClassName("positions1");
const jobtitles2 = document.getElementsByClassName("positions2");
const jobtitles3 = document.getElementsByClassName("positions3");
const jobitems1 = document.getElementsByClassName("loaded-jobs1");
const jobitems2 = document.getElementsByClassName("loaded-jobs2");
const jobitems3 = document.getElementsByClassName("loaded-jobs3");
const qrcodes1 = document.getElementsByClassName("qrcodes1");
const qrcodes2 = document.getElementsByClassName("qrcodes2");
const qrcodes3 = document.getElementsByClassName("qrcodes3");
const jobdescs1 = document.getElementsByClassName("jobdesc1");
const jobdescs2 = document.getElementsByClassName("jobdesc2");
const jobdescs3 = document.getElementsByClassName("jobdesc3");
var animation1 = document.getElementsByClassName("column-1");
var animation2 = document.getElementsByClassName("column-2");
var animation3 = document.getElementsByClassName("column-3");
var barAnimation = document.getElementsByClassName("in");

var index = 0;

var job1;
var job2;
var job3;
var jobitem1;
var jobitem2;
var jobitem3;
var qrCode1;
var qrCode2;
var qrCode3;
var jobdesc1;
var jobdesc2;
var jobdesc3;
var animation1clone;
var animation2clone;
var animation3clone;
var barAnimClone;

function hide(job, qrCode, jobitem, jobdesc) {
	job.classList.remove("displayed");
	job.classList.add("hidden");
	qrCode.classList.remove("displayed");
	qrCode.classList.add("hidden");
	jobitem.classList.remove("displayed");
	jobitem.classList.add("hidden");
	jobdesc.classList.remove("displayed");
	jobdesc.classList.add("hidden");
}

function fadein(job, qrCode, jobitem, jobdesc) {
	if (job.classList.contains("fadeout")) {
		job.classList.remove("fadeout");
	}
	job.classList.remove("hidden");
	job.classList.add("displayed");
	job.classList.add("fadein");
	if (jobitem.classList.contains("fadeout")) {
		jobitem.classList.remove("fadeout");
	}
	jobitem.classList.remove("hidden");
	jobitem.classList.add("displayed");
	jobitem.classList.add("fadein");
	if (qrCode.classList.contains("fadeout")) {
		qrCode.classList.remove("fadeout");
	}
	qrCode.classList.remove("hidden");
	qrCode.classList.add("displayed");
	qrCode.classList.add("fadein");
	if (jobdesc.classList.contains("fadeout")) {
		jobdesc.classList.remove("fadeout");
	}
	jobdesc.classList.remove("hidden");
	jobdesc.classList.add("displayed");
	jobdesc.classList.add("fadein");
}

function fadeout(job, qrCode, jobitem, jobdesc) {
	job.classList.remove("fadein");
	job.classList.add("fadeout");
	qrCode.classList.remove("fadein");
	qrCode.classList.add("fadeout");
	jobitem.classList.remove("fadein");
	jobitem.classList.add("fadeout");
	jobdesc.classList.remove("fadein");
	jobdesc.classList.add("fadeout");
}

function fade() {

	if (job1) {
		fadeout(job1, qrCode1, jobitem1, jobdesc1);
		setTimeout(function() {
			hide(job1, qrCode1, jobitem1, jobdesc1);
		}, 1000);
	}

	if (job2) {
		fadeout(job2, qrCode2, jobitem2, jobdesc2);
		setTimeout(function() {
			hide(job2, qrCode2, jobitem2, jobdesc2);
		}, 1000);
	}

	if (job3) {
		fadeout(job3, qrCode3, jobitem3, jobdesc3);
		setTimeout(function() {
			hide(job3, qrCode3, jobitem3, jobdesc3);
		}, 1000);
	}

	setTimeout(function() {
		if (index > (jobtitles1.length - 1)) {
			index = 0;
		}

		fadein(jobtitles1[index], qrcodes1[index], jobitems1[index],
				jobdescs1[index]);

		job1 = jobtitles1[index];
		jobitem1 = jobitems1[index];
		qrCode1 = qrcodes1[index];
		jobdesc1 = jobdescs1[index];

		index += 1;

		if (index > (jobtitles2.length - 1)) {
			index = 0;
		}

		fadein(jobtitles2[index], qrcodes2[index], jobitems2[index],
				jobdescs2[index]);

		job2 = jobtitles2[index];
		jobitem2 = jobitems2[index];
		qrCode2 = qrcodes2[index];
		jobdesc2 = jobdescs2[index];

		index += 1;

		if (index > (jobtitles3.length - 1)) {
			index = 0;
		}

		fadein(jobtitles3[index], qrcodes3[index], jobitems3[index],
				jobdescs3[index]);

		job3 = jobtitles3[index];
		jobitem3 = jobitems3[index];
		qrCode3 = qrcodes3[index];
		jobdesc3 = jobdescs3[index];

		index += 1;
	}, 1000);
}

fade();
setInterval(fade, 60000);