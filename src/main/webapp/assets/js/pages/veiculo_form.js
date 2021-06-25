/*!
 * OneUI - v4.7.0
 * @author pixelcave - https://pixelcave.com
 * Copyright (c) 2020
 */
! function(e) {
    jQuery.validator.addMethod(
        "money",
        function(value, element) {
            var isValidMoney = /^\d+(\.|\,)\d{2}$/.test(value);
            return this.optional(element) || isValidMoney;
        },
        "Insert "
    );

    var r = {};

    jQuery.validator.addMethod("validaRenavam", function ( renavam ) {
        var d = renavam.split("");
        soma = 0,
        valor = 0,
        digito = 0,
        x = 0;
      
        for (var i = 5; i >= 2; i--) {
          soma += d[x] * i;
          x++;
        }
      
        valor = soma % 11;
      
        if (valor == 11 || valor == 0 || valor >= 10) {
          digito = 0;
        } else {
          digito = valor;
        }
      
        if (digito == d[4]) {
          return true;
        } else {
          return false;
        }
      }, "Por favor, informe um renavam válido!" );

    function a(t) { if (r[t]) return r[t].exports; var n = r[t] = { i: t, l: !1, exports: {} }; return e[t].call(n.exports, n, n.exports, a), n.l = !0, n.exports }
    a.m = e, a.c = r, a.d = function(e, r, t) { a.o(e, r) || Object.defineProperty(e, r, { enumerable: !0, get: t }) }, a.r = function(e) { "undefined" != typeof Symbol && Symbol.toStringTag && Object.defineProperty(e, Symbol.toStringTag, { value: "Module" }), Object.defineProperty(e, "__esModule", { value: !0 }) }, a.t = function(e, r) {
        if (1 & r && (e = a(e)), 8 & r) return e;
        if (4 & r && "object" == typeof e && e && e.__esModule) return e;
        var t = Object.create(null);
        if (a.r(t), Object.defineProperty(t, "default", { enumerable: !0, value: e }), 2 & r && "string" != typeof e)
            for (var n in e) a.d(t, n, function(r) { return e[r] }.bind(null, n));
        return t
    }, a.n = function(e) { var r = e && e.__esModule ? function() { return e.default } : function() { return e }; return a.d(r, "a", r), r }, a.o = function(e, r) { return Object.prototype.hasOwnProperty.call(e, r) }, a.p = "", a(a.s = 14)
}({
    14: function(e, r, a) { e.exports = a(15) },
    15: function(e, r) {
        function a(e, r) {
            for (var a = 0; a < r.length; a++) {
                var t = r[a];
                t.enumerable = t.enumerable || !1, t.configurable = !0, "value" in t && (t.writable = !0), Object.defineProperty(e, t.key, t)
            }
        }
        var t = function() {
            function e() {! function(e, r) { if (!(e instanceof r)) throw new TypeError("Cannot call a class as a function") }(this, e) }
            var r, t, n;
            return r = e, n = [{
                key: "initValidation",
                value: function() {
                    One.helpers("validation"), jQuery(".js-info-validation").validate({
                            ignore: [],
                            rules: {
                                "veiculo.placa": {
                                    required: !0,
                                    minlength: 7,
                                    maxlength: 7,
                                    pattern: "[A-Za-z]{3}[0-9][0-9A-Za-z][0-9]{2}"
                                },
                                "veiculo.renavam": {
                                    required: !0,
                                    minlength: 11,
                                    validaRenavam: true,
                                    maxlength: 11,
                                    pattern: "[0-9]{11}"
                                },
                                "veiculo.modelo.id": {
                                    required: !0,
                                },
                                "veiculo.tipo": {
                                    required: !0
                                },
                                "veiculo.categoria.id": {
                                    required: !0
                                }
                            },
                            messages: {
                                "veiculo.placa": {
                                    required: "Informe a placa do veículo.",
                                    minlength: "A placa deve conter 7 dígitos.",
                                    maxlength: "A placa deve conter 7 dígitos.",
                                    pattern: "Formato inválido. A placa deve se estabelecer no padrão brasileiro ou do Mercosul!"
                                },
                                "veiculo.renavam": {
                                    required: "Informe o renavam do veículo.",
                                    minlength: "O renavam deve conter 11 dígitos.",
                                    maxlength: "O renavam deve conter 11 dígitos.",
                                    pattern: "Formato inválido. Digite apenas os números do renavam."
                                },
                                "veiculo.modelo.id": "Selecione o modelo do veículo.",
                                "veiculo.tipo": "Selecione o tipo do veículo.",
                                "veiculo.categoria": "Selecione a categoria do veículo."
                            }
                        }),
                        One.helpers("validation"), jQuery(".js-details-validation").validate({
                            ignore: [],
                            rules: {
                                "veiculo.qtdPassageiros": {
                                    required: !0,
                                    maxlength: 3,
                                    pattern: "[0-9]+"
                                },
                                "veiculo.anoFabricacao": {
                                    required: !0,
                                    maxlength: 4,
                                    pattern: "[0-9]+"
                                },
                                "veiculo.anoModelo": {
                                    required: !0,
                                    maxlength: 4,
                                    pattern: "[0-9]+"
                                },
                                "veiculo.quilometragem": {
                                    required: !0,
                                    pattern: "[0-9]+"
                                },
                                "veiculo.cor": {
                                    required: !0,
                                    maxlength: 20,
                                    pattern: "[A-Z a-z]+"
                                },
                                "veiculo.capacidadeTanque": {
                                    required: !0,
                                    maxlength: 4,
                                    pattern: "[0-9]+"
                                }
                            },
                            messages: {
                                "veiculo.qtdPassageiros": {
                                    required: "Informe a quantidade de passageiros do veículo.",
                                    maxlength: "Quantidade de passageiros inválida.",
                                    pattern: "Formato inválido. Digite apenas números."
                                },
                                "veiculo.anoFabricacao": {
                                    required: "Informe o ano de fabricação do veículo.",
                                    maxlength: "Ano de fabricação inválido.",
                                    pattern: "Formato inválido. Digite apenas números."
                                },
                                "veiculo.anoModelo": {
                                    required: "Informe a quantidade de passageiros do veículo.",
                                    maxlength: "Ano do modelo inválido.",
                                    pattern: "Formato inválido. Digite apenas números."
                                },
                                "veiculo.quilometragem": {
                                    required: "Informe a quilometragem atual do veículo.",
                                    pattern: "Formato inválido. Digite apenas números."
                                },
                                "veiculo.cor": {
                                    required: "Informe a cor do veículo.",
                                    maxlength: "A cor deve conter no máximo 20 caractéres.",
                                    pattern: "Formato inválido. Uma cor deve conter apenas letras"
                                },
                                "veiculo.capacidadeTanque": {
                                    required: "Informe a capacidade do tanque do veículo.",
                                    maxlength: "Capacidade inválida.",
                                    pattern: "Formato inválido. Digite apenas números"
                                }
                            }
                        }),
                        One.helpers("validation"), jQuery(".js-extras-validation").validate({
                            ignore: [],
                            rules: {
                                "veiculo.precoCompra": {
                                    required: !0,
                                    money: true
                                }
                            },
                            messages: {
                                "veiculo.precoCompra": {
                                    required: "Informe o preço de compra do veículo.",
                                    money: "Formato inválido"
                                }
                            }
                        })
                }
            }, { key: "init", value: function() { this.initValidation() } }], (t = null) && a(r.prototype, t), n && a(r, n), e
        }();
        jQuery((function() { t.init() }))
    }
});