const vw = Math.max(document.documentElement.clientWidth, window.innerWidth || 0);
const vh = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);

if(vw <= 1080 && vh <= 1920) {
	location.href="/screen1x1";
}

if(vw <= 3840  && vh <= 2160) {
	location.href="/screen2x2";
}