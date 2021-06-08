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
                            "veiculo.placa": {
                                required: !0,
                                minlength: 7,
                                maxlength: 7,
                                pattern: "[A-Za-z]{3}[0-9][0-9A-Za-z][0-9]{2}"
                            },
                            "veiculo.renavam": {
                                required: !0,
                                minlength: 11,
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
                            },
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
                            },
                            "veiculo.precoCompra": {
                                required: !0,
                                money: true
                            },
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
                            "veiculo.categoria": "Selecione a categoria do veículo.",
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
                            },
                            "veiculo.precoCompra": {
                                required: "Informe o preço de compra do veículo.",
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
});