$(function () {
    function showTime(){
        const date = new Date();

        let ye = new Intl.DateTimeFormat('en', { year: 'numeric' }).format(date);
        let mo = new Intl.DateTimeFormat('en', { month: 'short' }).format(date);
        let da = new Intl.DateTimeFormat('en', { day: '2-digit' }).format(date);
        let dw = new Intl.DateTimeFormat('en', { weekday: 'long' }).format(date);
        let tz = "Hanoi";

        let timeOption = {
            hour: 'numeric', minute: 'numeric', second: 'numeric',
            hour12: false,
            timeZone: 'Asia/Ho_Chi_Minh'
        };
        let time = new Intl.DateTimeFormat('en', timeOption).format(date);

        const dateTime = `${dw} ${da} - ${mo} - ${ye} | ${time} | ${tz}`;
        document.getElementById("MyClockDisplay").innerText = dateTime;
        document.getElementById("MyClockDisplay").textContent = dateTime;

        setTimeout(showTime, 1000);
    }

    showTime();
});
