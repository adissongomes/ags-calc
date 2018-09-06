<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">

    <style type="text/css">
        .t1, .t2, .t3, .t4, .t5, .t6 {
            border: 1px solid gray;
            width: 30px;
            height: 30px;
            margin-right: 5px;
            display: inline-block;
        }
        .t2 { border-left: 3px solid gray; }
        .t3 { border-left: 3px solid gray; border-top: 3px solid gray; }
        .t4 { border-left: 3px solid gray; border-right: 3px solid gray; }
        .t5 { border: 3px solid gray; border-right: 1px solid gray; }
        .t6 { border: 3px solid gray; }
    </style>

</head>

<body>

<h3>Dados da laje</h3>

<form class="laje" method="post" action="/Calcular">

    <label for="lx">Lx</label>
    <input id="lx" type="number" name="lx"/>
    <label for="ly">Ly</label>
    <input id="ly" type="number" name="ly"/>

    <br>
    <label>Contorno</label>
    <input type="radio" name="contorno" value="TYPE_1" checked/> <span class="t1"></span>
    <input type="radio" name="contorno" value="TYPE_2"/> <span class="t2"></span>
    <input type="radio" name="contorno" value="TYPE_3"/> <span class="t3"></span>
    <input type="radio" name="contorno" value="TYPE_4"/> <span class="t4"></span>
    <input type="radio" name="contorno" value="TYPE_5"/> <span class="t5"></span>
    <input type="radio" name="contorno" value="TYPE_6"/> <span class="t6"></span>

    <br>
    <label>Classe de Agressividade (CAA)</label>
    <input type="radio" name="caa" value="I"/> <span>I</span>
    <input type="radio" name="caa" value="II" checked/> <span>II</span>
    <input type="radio" name="caa" value="III"/> <span>III</span>
    <input type="radio" name="caa" value="IV"/> <span>IV</span>

    <br>
    <label >A&ccedil;o</label>
    <input type="radio" name="aco" value="CA25"/> <span>CA25</span>
    <input type="radio" name="aco" value="CA50"/> <span>CA50</span>
    <input type="radio" name="aco" value="CA60"/> <span>CA60</span>

    <br>
    <label for="h">Altura (cm)</label>
    <input id="h" type="number" name="h"/>

    <br>
    <label for="fck">Fck</label>
    <input id="fck" type="number" name="fck"/>

    <label for="e">Espessura concreto (mm)</label>
    <input id="e" type="number" name="e"/>

    <label for="earg">Espessura argamassa (mm)</label>
    <input id="earg" type="number" name="earg"/>

    <label for="emat">Espessura material (mm)</label>
    <input id="emat" type="number" name="emat"/>

    <label for="q">Carga acidental (kN/m<sup>2</sup>)</label>
    <input id="q" name="q" placeholder="0.00"/>

    <h4>Pesos especificos</h4>

    <label for="concretoArmado">Concreto armado (kN/m<sup>2</sup>)</label>
    <input id="concretoArmado" name="concretoArmado" value="25"/>
    <br>

    <label for="argamassa">Material (kN/m<sup>2</sup>)</label>
    <input id="argamassa" name="argamassa" value="21"/>
    <br>

    <label for="material">Material (kN/m<sup>2</sup>)</label>
    <input id="material" name="material" value="18"/>
    <br>

    <br>
    <button>Calcular</button>
</form>

</body>

</html>
