/*!
 * OneUI - v4.7.0
 * @author pixelcave - https://pixelcave.com
 * Copyright (c) 2020
 */
! function(a) {
    jQuery.validator.addMethod(
        "money",
        function(value, element) {
            var isValidMoney = /^\d+(\.|\,)\d{2}$/.test(value);
            return this.optional(element) || isValidMoney;
        },
        "Insert "
    );

    var e = {};
     // ----------------------------- VALIDAR CEP ------------------------------------------------

      jQuery.validator.addMethod("CEP", function( cep_value, element ) {
        return this.optional( element ) || /^\d{2}.\d{3}-\d{3}?$|^\d{5}-?\d{3}?$/.test( cep_value );
    }, "Informe um CEP válido." );


    jQuery.validator.addMethod( "validaCNPJ", function( value, element ) {
        "use strict";
    
        if ( this.optional( element ) ) {
            return true;
        }
    
        // Removing no number
        value = value.replace( /[^\d]+/g, "" );
    
        // Checking value to have 14 digits only
        if ( value.length !== 14 ) {
            return false;
        }
    
        // Elimina values invalidos conhecidos
        if ( value === "00000000000000" ||
            value === "11111111111111" ||
            value === "22222222222222" ||
            value === "33333333333333" ||
            value === "44444444444444" ||
            value === "55555555555555" ||
            value === "66666666666666" ||
            value === "77777777777777" ||
            value === "88888888888888" ||
            value === "99999999999999" ) {
            return false;
        }
    
        // Valida DVs
        var tamanho = ( value.length - 2 );
        var numeros = value.substring( 0, tamanho );
        var digitos = value.substring( tamanho );
        var soma = 0;
        var pos = tamanho - 7;
    
        for ( var i = tamanho; i >= 1; i-- ) {
            soma += numeros.charAt( tamanho - i ) * pos--;
            if ( pos < 2 ) {
                pos = 9;
            }
        }
    
        var resultado = soma % 11 < 2 ? 0 : 11 - soma % 11;
    
        if ( resultado !== parseInt( digitos.charAt( 0 ), 10 ) ) {
            return false;
        }
    
        tamanho = tamanho + 1;
        numeros = value.substring( 0, tamanho );
        soma = 0;
        pos = tamanho - 7;
    
        for ( var il = tamanho; il >= 1; il-- ) {
            soma += numeros.charAt( tamanho - il ) * pos--;
            if ( pos < 2 ) {
                pos = 9;
            }
        }
    
        resultado = soma % 11 < 2 ? 0 : 11 - soma % 11;
    
        if ( resultado !== parseInt( digitos.charAt( 1 ), 10 ) ) {
            return false;
        }
    
        return true;
    
    }, "Informe um CNPJ válido." );
    


     // ----------------------------- VALIDAR DATA DE NASCIMENTO ------------------------------------------------

      jQuery.validator.addMethod("dataNascimento", function(value){
        var data = value; // pega o valor do input
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
           
           return false;
        }
     
        if(idade >= 18 && idade <= 60){
          
           return true;
        }
        
        // se for maior que 60 não vai acontecer nada!
        return false;
     },"Idade mínima não atingida");



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
                        'cliente.nome': {
                            required: true,
                            minlength: 3,
                            pattern: "[A-Z a-z]+"
                        },
                        'cliente.registroGeral': {
                            required: true,
                            minlength: 5,
                            pattern: "[0-9]{7}"
                        },
                        'cliente.razaoSocial': {
                            required: true,
                            minlength: 3,
                            pattern: "[A-Z a-z 0-9]+" 
                        },
                        'cliente.cpfCnpj': {
                            required: true,
                            validaCNPJ: true
                        },
                        'endereco.descricao': {
                            required: true,
                            minlength: 3,
                            pattern: "[A-Z a-z 0-9]+" 
                        },
                        'endereco.cep': {
                            required: true,
                            minlength: 3,
                            CEP: true,
                            pattern: "[0-9]+"
                        },
                        'endereco.logradouro': {
                            required: true,
                            minlength: 3,
                            pattern: "[A-Z a-z 0-9]+" 
                        },
                        'endereco.numero': {
                            required: true,
                            minlength: 1,
                            pattern: "[0-9]+"                                
                        },
                        'endereco.complemento': {
                            required: true,
                            minlength: 3,
                            pattern: "[A-Z a-z 0-9]+"                              
                        },
                        'endereco.bairro': {
                            required: true,
                            minlength: 3,
                            pattern: "[A-Z a-z 0-9]+"  
                        },
                        'endereco.cidade': {
                            required: true,
                            minlength: 3,
                            pattern: "[A-Z a-z 0-9]+"  
                        },
                        'endereco.estado': {
                            required: !0  
                        },
                        'endereco.pais': {
                            required: true,
                            minlength: 3,
                            pattern: "[A-Z a-z]+"  
                        },
                        'endereco.tipo': {
                            required: !0, 
                        },
                        'email.email': {
                            required: true,
                            minlength: 3,
                            email: true 
                        },
                        'telefone.numero': {
                            required: true,
                            minlength: 8,
                            pattern: "[0-9]+"                        
                         },
                         'telefone.tipo': {
                            required: !0,                      
                         },
                    },
                    messages: {
                        'cliente.nome': {
                            required: 'Preencha o nome do cliente.',
                            minlength: 'O nome do cliente deve conter no mínimo 3 caractéres.'
                        },
                        'cliente.registroGeral': {
                            required: 'Informe o RG do cliente.',
                            minlength: 'O RG deve conter 7 dígitos',
                            pattern : 'Preencha o RG apenas com números'
                        },
                        'cliente.cpfCnpj': {
                            required: 'Informe o CNPJ do cliente.',
                        },
                        'endereco.descricao': {
                            required: 'Preencha o endereço do cliente.',
                            minlength: 'A descrição do endereço deverá conter pelo menos 4 caracteres.',
                            pattern : 'Informe o endereço apenas com números ou letras.'
                        },
                        'endereco.cep': {
                            required: 'Informe o CEP do motorista.',
                            minlength: 'O CEP deverá conter 8 números',
                            pattern : 'Informe o CEP apenas com  números.'
                        },
                        'endereco.logradouro': {
                            required: 'Preencha o logradouro do cliente.',
                            minlength: 'O lougradoro deverá conter pelo menos 4 caracteres.',
                            pattern : 'Informe o logradouro apenas com números ou letras.'
                        },
                        'endereco.numero': {
                            required: 'Informe o número do endereço.',
                            minlength: 'Número inválido',
                            pattern : 'O número do endereço é inválido.'
                        },
                        'endereco.complemento': {
                            required: 'Preencha o complemento de endereço do cliente.',
                            minlength: 'O complemento de endereço deverá conter pelo menos 4 caracteres.',
                            pattern : 'Informe o complemento apenas com números ou letras.'
                        },
                        'endereco.bairro': {
                            required: 'Preencha o bairro do cliente.',
                            minlength: 'A bairro deverá conter pelo menos 3 caracteres.',
                            pattern : 'Informe o bairro apenas com números ou letras.'
                        },
                        'endereco.cidade': {
                            required: 'Preencha a cidade do cliente.',
                            minlength: 'A cidade deverá conter pelo menos 3 caracteres.',
                            pattern : 'Informe apenas letras.'
                        },
                        'endereco.estado': {
                            required: 'Selecione o estado do cliente.',
                        },
                        'endereco.pais': {
                            required: 'Preencha o pais do cliente.',
                            minlength: 'O pais deverá conter pelo menos 3 caracteres.',
                            pattern : 'Informe apenas letras.'
                        },
                        'endereco.tipo': 'Selecione um tipo de endereço.',
                        'email.email': 'Preencha um email válido',
                        'telefone.numero': {
                            required: 'Informe o número de telefone do motorista.',
                            minlength: 'O número deverá conter no mínimo 10 números',
                            pattern : 'Informe apenas números.'
                        },
                        'telefone.tipo': 'Selecione um tipo de telefone.',
                        
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
});