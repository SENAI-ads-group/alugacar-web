/*!
 * OneUI - v4.7.0
 * @author pixelcave - https://pixelcave.com
 * Copyright (c) 2020
 */
! function(a) {
    var e = {};



    
    function i(t) { if (e[t]) return e[t].exports; var r = e[t] = { i: t, l: !1, exports: {} }; return a[t].call(r.exports, r, r.exports, i), r.l = !0, r.exports }
    i.m = a, i.c = e, i.d = function(a, e, t) { i.o(a, e) || Object.defineProperty(a, e, { enumerable: !0, get: t }) }, i.r = function(a) { "undefined" != typeof Symbol && Symbol.toStringTag && Object.defineProperty(a, Symbol.toStringTag, { value: "Module" }), Object.defineProperty(a, "__esModule", { value: !0 }) }, i.t = function(a, e) {
        if (1 & e && (a = i(a)), 8 & e) return a;
        if (4 & e && "object" == typeof a && a && a.__esModule) return a;
        var t = Object.create(null);
        if (i.r(t), Object.defineProperty(t, "default", { enumerable: !0, value: a }), 2 & e && "string" != typeof a)
            for (var r in a) i.d(t, r, function(e) { return a[e] }.bind(null, r));
        return t
    }, i.n = function(a) { var e = a && a.__esModule ? function() { return a.default } : function() { return a }; return i.d(e, "a", e), e }, i.o = function(a, e) { return Object.prototype.hasOwnProperty.call(a, e) }, i.p = "", i(i.s = 16)
}({
    16: function(a, e, i) { a.exports = i(17) },
    17: function(a, e) {
        function i(a, e) {
            for (var i = 0; i < e.length; i++) {
                var t = e[i];
                t.enumerable = t.enumerable || !1, t.configurable = !0, "value" in t && (t.writable = !0), Object.defineProperty(a, t.key, t)
            }
        }
        var t = function() {
            function a() {! function(a, e) { if (!(a instanceof e)) throw new TypeError("Cannot call a class as a function") }(this, a) }
            var e, t, r;
            return e = a, r = [{ key: "initWizardDefaults", value: function() { jQuery.fn.bootstrapWizard.defaults.tabClass = "nav nav-tabs", jQuery.fn.bootstrapWizard.defaults.nextSelector = '[data-wizard="next"]', jQuery.fn.bootstrapWizard.defaults.previousSelector = '[data-wizard="prev"]', jQuery.fn.bootstrapWizard.defaults.firstSelector = '[data-wizard="first"]', jQuery.fn.bootstrapWizard.defaults.lastSelector = '[data-wizard="lsat"]', jQuery.fn.bootstrapWizard.defaults.finishSelector = '[data-wizard="finish"]', jQuery.fn.bootstrapWizard.defaults.backSelector = '[data-wizard="back"]' } }, {
                key: "initWizardSimple",
                value: function() {
                    jQuery(".js-wizard-simple").bootstrapWizard({
                        onTabShow: function(a, e, i) {
                            var t = (i + 1) / e.find("li").length * 100,
                                r = e.parents(".block").find('[data-wizard="progress"] > .progress-bar');
                            r.length && r.css({ width: t + 1 + "%" })
                        }
                    })
                }
            }, {
                key: "initWizardValidation",
                value: function() {
                    One.helpers("validation");
                    var a = jQuery(".js-validation-form"),
                        e = jQuery(".js-wizard-validation2-form");
                    a.add(e).on("keyup keypress", (function(a) {
                        if (13 === (a.keyCode || a.which) && "textarea" !== a.target.tagName.toLowerCase()) return a.preventDefault(), !1
                    }));
                    var i = a.validate({
                        rules: {
                            'locacao.dataRetirada': {
                                required: true,                                
                                pattern: Date 
                            },
                            'locacao.dataDevolucao': {
                                required: true,
                                pattern: Date
                            },
                            'locacao.motorista.nome': {
                                required: true,
                                minlength: 3,
                                pattern: "[A-Z a-z]+"
                            },
                            'locacao.motorista.dataNascimento': {
                                required: true,
                                pattern: Date
                            },
                            'locacao.motorista.registroGeral': {
                                required: true,
                                minlength: 5,
                                pattern: "[0-9]{7}"
                            },
                            'locacao.motorista.registroCNH': {
                                required: true,
                                minlength: 5,
                                pattern: "[0-9]{10}"
                            },
                            'locacao.motorista.categoriaCNH': {
                                required: true,
                                minlength: 1,
                                pattern: "[A-Z]+"
                            },
                            'locacao.motorista.validadeCNH': {
                                required: true,
                                pattern: Date
                            },
                            'locacao.motorista.cpf': {
                                required: true,
                                minlength: 11,
                                pattern: "[0-9]{11}"
                            },
                            'locacao.veiculo.id': {
                                required: !0,
                            },
                            'locacao.apolice.dataInicio': {
                                required: true,                                
                                pattern: Date
                            },
                            'locacao.apolice.dataFim': {
                                required: true,                                
                                pattern: Date
                            },
                            'locacao.apolice.valor': {
                                required: !0,
                                money: true
                            },
                            'locacao.valorSeguro': {
                                required: !0,
                                money: true
                            },
                            'locacao.valorCalcao': {
                                required: !0,
                                money: true
                            },
                        },
                        messages: {
                            'locacao.dataRetirada': {
                                required: 'Preencha a data de retirada.'
                            },
                            'locacao.dataDevolucao': {
                                required: 'Preencha a data de devolução.',
                            },
                            'locacao.motorista.nome': {
                                required: 'Preencha o nome do motorista.',
                                minlength: 'O nome do motorista deve conter no mínimo 3 caractéres'
                            },
                            'locacao.motorista.nascimento': {
                                required: 'Preencha o ano de nascimento do motorista.',
                                
                            },
                            'locacao.motorista.registroGeral': {
                                required: 'Informe o RG do motorista.',
                                minlength: 'O RG deve conter 7 dígitos',
                                pattern : 'Informe apenas números.'
                            },
                            'locacao.motorista.cpf': {
                                required: 'Informe o CPF do motorista.',
                                minlength: 'O CPF deve conter 11 dígitos',
                                pattern : 'Informe apenas números.'
                            },
                            'locacao.motorista.registroCNH': {
                                required: 'Informe o registro da CNH do motorista.',
                                minlength: 'O registro deve conter 10 dígitos',
                                pattern : 'Informe apenas números.'
                            },
                            'locacao.motorista.categoriaCNH': {
                                required: 'Informe a categoria da CNH do motorista.',
                                minlength: 'O registro deve conter no mínimo 1 dígito',
                                pattern : 'Informe apenas números.'
                            },
                            "locacao.veiculo.id": "Selecione um veículo para ser locado.",
                            'locacao.apolice.dataInicio': {
                                required: 'Preencha a data de início da apólice.',
                                
                            },
                            'locacao.apolice.dataFim': {
                                required: 'Preencha a data do fim da apólice.',
                                
                            },
                            "locacao.apolice.valor": {
                                required: "Informe o valor da apólice.",
                                money: "Formato inválido"
                            },
                            "locacao.valorSeguro": {
                                required: "Informe o valor do seguro.",
                                money: "Formato inválido"
                            },
                            "locacao.valorCalcao": {
                                required: "Informe o valor do calção.",
                                money: "Formato inválido"
                            }
                        }
                    });
                    jQuery(".js-wizard-validation").bootstrapWizard({
                        tabClass: "",
                        onTabShow: function(a, e, i) {
                            var t = (i + 1) / e.find("li").length * 100,
                                r = e.parents(".block").find('[data-wizard="progress"] > .progress-bar');
                            r.length && r.css({ width: t + 1 + "%" })
                        },
                        onNext: function(e, t, r) { if (!a.valid()) return i.focusInvalid(), !1 },
                        onTabClick: function(a, e, i) { return jQuery("a", e).blur(), !1 }
                    });
                }
            }, { key: "init", value: function() { this.initWizardDefaults(), this.initWizardSimple(), this.initWizardValidation() } }], (t = null) && i(e.prototype, t), r && i(e, r), a
        }();
        jQuery((function() { t.init() }))
    }
})

$.validator.addMethod( "locacao.motorista.registroCNH", function( value ) {

    // Removing special characters from value
    value = value.replace( /([~!@#$%^&*()_+=`{}\[\]\-|\\:;'<>,.\/? ])+/g, "" );
  
    // Checking value to have 11 digits only
    if ( value.length !== 11 ) {
      return false;
    }
  
    var sum = 0, dsc = 0, firstChar,
          firstCN, secondCN, i, j, v;
  
    firstChar = value.charAt( 0 );
  
    if ( new Array( 12 ).join( firstChar ) === value ) {
      return false;
    }
  
    // Step 1 - using first Check Number:
    for ( i = 0, j = 9, v = 0; i < 9; ++i, --j ) {
      sum += +( value.charAt( i ) * j );
    }
  
    firstCN = sum % 11;
    if ( firstCN >= 10 ) {
      firstCN = 0;
      dsc = 2;
    }
  
    sum = 0;
    for ( i = 0, j = 1, v = 0; i < 9; ++i, ++j ) {
      sum += +( value.charAt( i ) * j );
    }
  
    secondCN = sum % 11;
    if ( secondCN >= 10 ) {
      secondCN = 0;
    } else {
      secondCN = secondCN - dsc;
    }
  
    return ( String( firstCN ).concat( secondCN ) === value.substr( -2 ) );
  
  }, "Por favor, informe um número de CNH válido!" );

function validadataNascimento(){
    var data = document.getElementById("locacao.motorista.nascimento").value; // pega o valor do input
    data = data.replace(/\//g, "-"); // substitui eventuais barras (ex. IE) "/" por hífen "-"
    var data_array = data.split("-"); // quebra a data em array
    
    // para o IE onde será inserido no formato dd/MM/yyyy
    if(data_array[0].length != 4){
       data = data_array[2]+"-"+data_array[1]+"-"+data_array[0]; // remonto a data no formato yyyy/MM/dd
    }
    
    // comparo as datas e calculo a idade
    var hoje = new Date();
    var nasc  = new Date(data);
    var idade = hoje.getFullYear() - nasc.getFullYear();
    var m = hoje.getMonth() - nasc.getMonth();
    if (m < 0 || (m === 0 && hoje.getDate() < nasc.getDate())) idade--;
    
    if(idade < 18){
       alert("Pessoas menores de 18 não podem se cadastrar.");
       return false;
    }
 
    if(idade >= 18 && idade <= 60){
       alert("Maior de 18, pode se cadastrar.");
       return true;
    }
    
    // se for maior que 60 não vai acontecer nada!
    return false;
 }

 function validadataValidade(){
    var data = document.getElementById("locacao.motorista.validade").value; // pega o valor do input
    data = data.replace(/\//g, "-"); // substitui eventuais barras (ex. IE) "/" por hífen "-"
    var data_array = data.split("-"); // quebra a data em array
    
    // para o IE onde será inserido no formato dd/MM/yyyy
    if(data_array[0].length != 4){
       data = data_array[2]+"-"+data_array[1]+"-"+data_array[0]; // remonto a data no formato yyyy/MM/dd
    }
    
    // comparo as datas e calculo a idade
    var hoje = new Date();
    var validade  = new Date(data);
    var tempo = hoje.getFullYear() - validade.getFullYear();
    var m = hoje.getMonth() - validade.getMonth();
    if (m < 0 || (m === 0 && hoje.getDate() < nasc.getDate())) tempo--;
    
    if(hoje.getDate > validade.getDate){
       alert("A validade da carteira de motorista expirou");
       return false;
    }
 
    if(idade >= 18 && idade <= 60){
       alert("Maior de 18, pode se cadastrar.");
       return true;
    }
    
    // se for maior que 60 não vai acontecer nada!
    return false;
 }
 jQuery.validator.addMethod("registroCNH", function( value ) {

    // Removing special characters from value
    value = value.replace( /([~!@#$%^&*()_+=`{}\[\]\-|\\:;'<>,.\/? ])+/g, "" );
  
    // Checking value to have 11 digits only
    if ( value.length !== 11 ) {
      return false;
    }
  
    var sum = 0, dsc = 0, firstChar,
          firstCN, secondCN, i, j, v;
  
    firstChar = value.charAt( 0 );
  
    if ( new Array( 12 ).join( firstChar ) === value ) {
      return false;
    }
  
    // Step 1 - using first Check Number:
    for ( i = 0, j = 9, v = 0; i < 9; ++i, --j ) {
      sum += +( value.charAt( i ) * j );
    }
  
    firstCN = sum % 11;
    if ( firstCN >= 10 ) {
      firstCN = 0;
      dsc = 2;
    }
  
    sum = 0;
    for ( i = 0, j = 1, v = 0; i < 9; ++i, ++j ) {
      sum += +( value.charAt( i ) * j );
    }
  
    secondCN = sum % 11;
    if ( secondCN >= 10 ) {
      secondCN = 0;
    } else {
      secondCN = secondCN - dsc;
    }
  
    return ( String( firstCN ).concat( secondCN ) === value.substr( -2 ) );
  
  }, "Por favor, informe um número de CNH válido!" );
 
 
 ;