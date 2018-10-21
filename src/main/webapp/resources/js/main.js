(function () {
    loadSelectValues();

    jsf.ajax.addOnEvent(function(event) {

        try {
            if (event.status === 'success' && event.source.id === 'calculate') {
                var data = JSON.parse(document.getElementById("result").value);
                desenhaRepresantacao(data.n1, data.n2);

                document.getElementById("lx-col").innerText = data.lx;
                document.getElementById("ly-col").innerText = data.ly;
                document.getElementById("mx-col").innerText = data.momento.mx;
                document.getElementById("my-col").innerText = data.momento.my;
                document.getElementById("p-col").innerText = data.carregamento.p;
                document.getElementById("n1-col").innerText = data.n1;
                document.getElementById("n2-col").innerText = data.n2;

                PF('memorial').show();
            }
        } catch (exception) {
            console.warn(exception);
            desenhaRepresantacao(0, 0);
        }
    });

})();

function loadSelectValues() {
    var contorno = document.getElementById("contorno").value;
    var caa = document.getElementById("caa").value;
    var aco = document.getElementById("aco").value;

    var element = document.querySelector("[type=radio][value=" + contorno + "]");
    if (element)
        element.click();

    element = document.querySelector("[type=radio][value=" + caa + "]");
    if (element)
        element.click();

    element = document.querySelector("[type=radio][value=" + aco + "]");
    if (element)
        element.click();
}

function alteraContorno(value) {
    document.getElementById("contorno").value = value;
}

function alteraCAA(value) {
    document.getElementById("caa").value = value;
}

function alteraAco(value) {
    document.getElementById("aco").value = value;
}

function desenhaRepresantacao(n1, n2) {
    console.log('Representacao para laje...');
    console.log('n1: ' + n1);
    console.log('n2: ' + n2);

    var canvas = document.getElementById("canvas");
    var ctx = canvas.getContext("2d");
    ctx.clearRect(0, 0, canvas.width, canvas.height);

    ctx.strokeRect(40, 40, 100, 120);

    ctx.beginPath();
    // n1
    ctx.moveTo(105, 40);
    ctx.lineTo(105, 160);
    // n2
    ctx.moveTo(40, 120);
    ctx.lineTo(140, 120);
    ctx.closePath();
    ctx.stroke();

    ctx.font = '10pt sans-serif';
    ctx.fillText(n1, 105, 30);
    ctx.fillText(n2, 150, 120);
}

function openAgregadosDialog(inputId) {
    PF('agregados').show();
    selectTableValueToInput('agregados-table', inputId, 2);
}

function openPesosDialog(inputId) {
    PF('pesos').show();
    selectTableValueToInput('pesos-table', inputId, 2);
}

function selectTableValueToInput(tableId, inputId, childIndex) {
    $('#'+ tableId + ' tr').click(function(element) {
        var value = $(this).find('td:nth-child(' + childIndex + ')')[0].innerText;
        if (inputId) {
            var input = document.getElementById(inputId);
            input.value = value;
        }
    });
}