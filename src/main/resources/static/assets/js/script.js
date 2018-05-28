function reset() {
	$.post({
		url : "reset",
		success : function() {
			location.reload();
		}
	});
}

function resetTo(place) {
	$.post({
		url : "resetTo",
		data : {
			place : place
		},
		success : function() {
			location.reload();
		}
	});
}

$(function() {
	if (!('getContext' in document.createElement('canvas'))) {
		alert('Sorry, it looks like your browser does not support canvas!');
		return false;
	}

	var canvas = document.getElementById("board");
	var ctx = canvas.getContext('2d')
	var boardSize = 8
	var rect = canvas.parentNode.getBoundingClientRect();
	canvas.width = rect.width;
	canvas.height = rect.height;

	var rectSize = Math.min(canvas.width, canvas.height) * .8 / boardSize
	var startX = canvas.width * .5 - (rectSize * (boardSize / 2));
	var startY = 0;
	canvas.addEventListener('click', click);
	drawBoard();

	function click(event) {
		var x = event.clientX
		var y = event.clientY
		if (x < startX || x > (startX + rectSize * boardSize) || y < startY
				|| y > (startY + rectSize * boardSize + canvas.offsetTop)) {
			return

		}
		x -= startX
		y -= canvas.offsetTop;

		var xCell = parseInt(x / rectSize);
		var yCell = parseInt(y / rectSize);
		var gameOver = false;
		$.ajax({
			type : 'POST',
			url : "place",
			data : {
				x : xCell,
				y : yCell
			},
			success : function(gameState) {
				if (gameState.gameOver) {
					$.post("reset");
				} else {
					drawBoard();
				}
			},
			complete : function() {
				$.get("state", function(gameState) {
					if (gameState.currentTurn === 'WHITE') {
						window.setTimeout(function() {
							$.ajax({
								type : "GET",
								url : "move",
								success : function(gameState) {
									$("#turn").text(
											gameState.currentTurn + "s turn")
								},
								complete : function() {
									drawBoard();
								}

							})
						}, 1000)
					}
					;
				});
			}
		});
	}

	function drawBoard() {
		ctx.fillStyle = "#0a870a";
		ctx
				.fillRect(startX, startY, rectSize * boardSize, rectSize
						* boardSize);
		var path = new Path2D;
		board = new Array(boardSize);
		$.get("board", function(data) {
			board = data;
			for (i = 0; i < boardSize; i++) {
				for (j = 0; j < boardSize; j++) {
					path.rect(startX + rectSize * i, startY + rectSize * j,
							rectSize, rectSize);
					if (board[j][i] == 1) {
						drawCircle(i, j, 'black')
					} else if (board[j][i] == 2) {
						drawCircle(i, j, 'white')
					}
				}
			}
			ctx.stroke(path);
		});

		$.get("state", function(gameState) {
			$("#turn").text(gameState.currentTurn + "s turn")
			$("#black_score").text("Black score: " + gameState.blackScore);
			$("#white_score").text("White score: " + gameState.whiteScore);
			$("#moves").text("");
			var i;
			for (i = 0; i < gameState.moveHistory.length; i++) {
				$("#moves").append(
						"<a onclick=\"javascript:resetTo(" + (i + 1) + ")\">"
								+ gameState.moveHistory[i] + "</a> ")
			}
			;
		});
	}

	function drawCircle(x, y, color) {
		var path = new Path2D
		var circleSize = rectSize / 2
		ctx.beginPath();
		ctx.arc(startX + (rectSize * x) + circleSize, startY + (rectSize * y)
				+ circleSize, circleSize, 0, 2 * Math.PI)

		ctx.fillStyle = color
		ctx.fill()
		ctx.stroke();

	}
});
