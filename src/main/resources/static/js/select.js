const vw = Math.max(document.documentElement.clientWidth, window.innerWidth || 0);
const vh = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);

if(vw <= 1080) {
	location.href="/team1/screen1x1";
} else {
	location.href="/team1/screen2x2";
}