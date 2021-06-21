/*!
 * OneUI - v4.7.0
 * @author pixelcave - https://pixelcave.com
 * Copyright (c) 2020
 */
! function (e) {
    jQuery.validator.addMethod(
        "money",
        function (value, element) {
            var isValidMoney = /^\d+(\.|\,)\d{2}$/.test(value);
            return this.optional(element) || isValidMoney;
        },
        "Insert "
    );

    var r = {};

    function a(t) { if (r[t]) return r[t].exports; var n = r[t] = { i: t, l: !1, exports: {} }; return e[t].call(n.exports, n, n.exports, a), n.l = !0, n.exports }
    a.m = e, a.c = r, a.d = function (e, r, t) { a.o(e, r) || Object.defineProperty(e, r, { enumerable: !0, get: t }) }, a.r = function (e) { "undefined" != typeof Symbol && Symbol.toStringTag && Object.defineProperty(e, Symbol.toStringTag, { value: "Module" }), Object.defineProperty(e, "__esModule", { value: !0 }) }, a.t = function (e, r) {
        if (1 & r && (e = a(e)), 8 & r) return e;
        if (4 & r && "object" == typeof e && e && e.__esModule) return e;
        var t = Object.create(null);
        if (a.r(t), Object.defineProperty(t, "default", { enumerable: !0, value: e }), 2 & r && "string" != typeof e)
            for (var n in e) a.d(t, n, function (r) { return e[r] }.bind(null, n));
        return t
    }, a.n = function (e) { var r = e && e.__esModule ? function () { return e.default } : function () { return e }; return a.d(r, "a", r), r }, a.o = function (e, r) { return Object.prototype.hasOwnProperty.call(e, r) }, a.p = "", a(a.s = 14)
}({
    14: function (e, r, a) { e.exports = a(15) },
    15: function (e, r) {
        function a(e, r) {
            for (var a = 0; a < r.length; a++) {
                var t = r[a];
                t.enumerable = t.enumerable || !1, t.configurable = !0, "value" in t && (t.writable = !0), Object.defineProperty(e, t.key, t)
            }
        }
        var t = function () {
            function e() { ! function (e, r) { if (!(e instanceof r)) throw new TypeError("Cannot call a class as a function") }(this, e) }
            var r, t, n;
            return r = e, n = [{
                key: "initValidation",
                value: function () {
                    One.helpers("validation"), jQuery(".js-info-validation").validate({
                        ignore: [],
                        rules: {
                            'usuario.nome': {
                                required: true,
                                minlength: 3
                            },
                            'usuario.email': {
                                required: true,
                                email: true
                            }
                        },
                        messages: {
                            'usuario.nome': {
                                required: 'Preencha seu nome de usuário',
                                minlength: 'Seu nome de usuário deve conter no mínimo 3 caractéres'
                            },
                            'usuario.email': 'Preencha um email válido',
                        }
                    }),
                        One.helpers("validation"), jQuery(".js-foto-validation").validate({
                            ignore: [],
                            rules: {
                                "foto": {
                                    required: true
                                }
                            },
                            messages: {
                                'foto': 'Selecione uma foto'
                            }
                        }),
                        One.helpers("validation"), jQuery(".js-senha-validation").validate({
                            ignore: [],
                            rules: {
                                'senhaAtual': {
                                    required: true
                                },
                                'novaSenha': {
                                    required: true,
                                    minlength: 5,
                                    maxlength: 10
                                },
                                'confirmacaoSenha': {
                                    required: true,
                                    equalTo: '#novaSenha'
                                },
                            },
                            messages: {
                                'senhaAtual': 'Informe a senha atual',
                                'novaSenha': {
                                    required: 'Informe a nova senha',
                                    minlength: 'A nova senha deve conter no mínimo 5 caractéres.',
                                    maxlength: 'A nova senha deve conter no máximo 10 caractéres.'
                                },
                                'confirmacaoSenha': {
                                    required: true,
                                    equalTo: '#novaSenha'
                                }
                            }
                        })
                }
            }, { key: "init", value: function () { this.initValidation() } }], (t = null) && a(r.prototype, t), n && a(r, n), e
        }();
        jQuery((function () { t.init() }))
    }
});