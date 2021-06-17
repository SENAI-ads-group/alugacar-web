! function (e) {

    jQuery.validator.addMethod("valueNotEquals", function (value, element, arg) {
        return arg != element.value;
    }, "Value must not equal arg.");

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
                    One.helpers("validation"), jQuery(".js-validation-form").validate({
                        ignore: [],
                        rules: {
                            'telefone.numero': {
                                required: true,
                                minlength: 10,
                                maxlength: 11,
                                pattern: "[0-9]+"
                            },
                            'telefone.tipo': {
                                valueNotEquals: "0",
                                required: true
                            }
                        },
                        messages: {
                            'telefone.numero': {
                                required: 'Informe o telefone',
                                minlength: 'O telefone deve conter 10 digitos para telefones fixos e 11 digítos para telefones móveis.',
                                maxlength: 'O telefone deve conter 10 digitos para telefones fixos e 11 digítos para telefones móveis.',
                                pattern: "Formato inválido. Digite apenas números"
                            },
                            'telefone.tipo': {
                                required: 'Selecione o tipo de telefone.',
                                valueNotEquals: 'Selecione o tipo de telefone.'
                            },
                        }
                    })
                }
            }, { key: "init", value: function () { this.initValidation() } }], (t = null) && a(r.prototype, t), n && a(r, n), e
        }();
        jQuery((function () { t.init() }))
    }
});