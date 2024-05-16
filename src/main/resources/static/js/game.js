let selectedCell = null;
function makeMove(e){
    const cell = $(e.target).closest('.cell')[0]
    if(selectedCell === null){
        selectedCell = cell.dataset;
        return;
    }
    sendMove(selectedCell, cell.dataset);
    selectedCell = null;
}
function sendMove(e, e1) {
    var destination = "/app/game/"+document.getElementById("sender").textContent + '_'+document.getElementById("recipient").textContent;
    console.log(e, e1);
    stompClient.publish({
        destination: destination,
        body: JSON.stringify({'row': e.row, 'column': e.cell, 'newRow': e1.row, 'newColumn': e1.cell, 'userName': currentUser})
    });
}

function showBoard(moveResult) {
    if(moveResult === null){
        return;
    }
    for (let row = 0; row < moveResult.board.length; row++) {
        for (let cell = 0; cell < moveResult.board[row].length; cell++) {
            if (moveResult.board[row][cell] === null) {
                document.querySelector('[data-row="' + row + '"][data-cell="' + cell + '"]').innerHTML = ``;
                continue
            }
            const {x, y, color, type} = moveResult.board[row][cell]
            console.log(moveResult.board[row][cell])
            document.querySelector('[data-row="' + x + '"][data-cell="' + y + '"]').innerHTML = `<img src="/assets/figures/${color}/${type}.svg"/>`;
        }
    }

    if(moveResult.message !== 'play'){
        var res = document.querySelector('.res');
        var h1Element = document.createElement('h1');
        h1Element.textContent = moveResult.message;
        res.style.display = 'block';
        res.appendChild(h1Element);
    }
}

$(function () {
    $( ".cell" ).click((e) => makeMove(e));
});
