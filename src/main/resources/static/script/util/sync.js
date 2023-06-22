let playListenerInterval;
let stillSyncPlay = true;


function sync(audio, sessionId) {
    $.ajax({
        url: '/stream/sync/' + sessionId + '/' + audio.currentTime,
        type: 'POST',
        success: syncResult => {
            console.log(syncResult.needPlay);
            if (!syncResult.needPlay) {
                audio.pause();
                playListenerInterval = setInterval(() => sync(audio, sessionId), 1000);
            } else {
                if (stillSyncPlay) {
                    clearInterval(playListenerInterval);
                    audio.play();
                }
            }
        }
    });
}
