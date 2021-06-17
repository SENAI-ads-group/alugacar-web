/*!
 * OneUI - v4.7.0
 * @author pixelcave - https://pixelcave.com
 * Copyright (c) 2020
 */

! function(e) {
    var r = {};

    function n(t) { if (r[t]) return r[t].exports; var i = r[t] = { i: t, l: !1, exports: {} }; return e[t].call(i.exports, i, i.exports, n), i.l = !0, i.exports }
    n.m = e, n.c = r, n.d = function(e, r, t) { n.o(e, r) || Object.defineProperty(e, r, { enumerable: !0, get: t }) }, n.r = function(e) { "undefined" != typeof Symbol && Symbol.toStringTag && Object.defineProperty(e, Symbol.toStringTag, { value: "Module" }), Object.defineProperty(e, "__esModule", { value: !0 }) }, n.t = function(e, r) {
        if (1 & r && (e = n(e)), 8 & r) return e;
        if (4 & r && "object" == typeof e && e && e.__esModule) return e;
        var t = Object.create(null);
        if (n.r(t), Object.defineProperty(t, "default", { enumerable: !0, value: e }), 2 & r && "string" != typeof e)
            for (var i in e) n.d(t, i, function(r) { return e[r] }.bind(null, i));
        return t
    }, n.n = function(e) { var r = e && e.__esModule ? function() { return e.default } : function() { return e }; return n.d(r, "a", r), r }, n.o = function(e, r) { return Object.prototype.hasOwnProperty.call(e, r) }, n.p = "", n(n.s = 38)
}({
    38: function(e, r, n) { e.exports = n(39) },
    39: function(e, r) {
        function n(e, r) {
            for (var n = 0; n < r.length; n++) {
                var t = r[n];
                t.enumerable = t.enumerable || !1, t.configurable = !0, "value" in t && (t.writable = !0), Object.defineProperty(e, t.key, t)
            }
        }
        var t = function() {
            function e() {! function(e, r) { if (!(e instanceof r)) throw new TypeError("Cannot call a class as a function") }(this, e) }
            var r, t, i;
            return r = e, i = [{
                key: "initValidation",
                value: function() {
                    One.helpers("validation"),
                        jQuery(".js-validation-form").validate({
                            rules: {
                                'vistoria.dataRet': {
                                    required: true,
                                    minlength: 8,
                                    maxlength: 8,
                                    pattern: date
                                },
                                "vistoria.combustivelRet": {
                                    required: !0,
                                    minlength: 1, 
                                    pattern: "[0-9]"
                                },
                                "vistoria.quilometragemRet": {
                                    required: !0,
                                    minlength: 1, 
                                    pattern: "[0-9]"
                                },
                                "vistoria.observacaoRet": {
                                    required: 0,
                                    pattern: text
                                }
                            },
                            messages: {
                                'vistoria.dataRet': {
                                    required: 'Preencha a data de retirada.',
                                    minlength: 'A data deve ser informada no formato dd/mm/yyyy.',
                                    maxlength: 'A data deve ser informada no formato dd/mm/yyyy.'
                                },
                                'vistoria.combustivelRet': {
                                    required: 'Preencha o nível de combustível do veículo.',
                                    minlength: 'Informe um valor válido.'
                                    
                                },
                                'vistoria.quilometragemRet': {
                                    required: 'Preencha a quilometragem atual do veículo.',
                                    minlength: 'Informe uma quilometragem válida.'
                                    
                                },
                            }
                        })
                }
            }, { key: "init", value: function() { this.initValidation() } }], (t = null) && n(r.prototype, t), i && n(r, i), e
        }();
        jQuery((function() { t.init() }))
    }
})

$("#vistoria.dataRet").mask("99/99/9999");

document.getElementById("foto").onchange = function () {
    var reader = new FileReader();
    if(this.files[0].size>528385){
        alert("Image Size should not be greater than 500Kb");
        $("#foto").attr("src","blank");
        $("#foto").hide();  
        $('#foto').wrap('<form>').closest('form').get(0).reset();
        $('#foto').unwrap();     
        return false;
    }
    if(this.files[0].type.indexOf("image")==-1){
        alert("Invalid Type");
        $("#foto").attr("src","blank");
        $("#foto").hide();  
        $('#foto').wrap('<form>').closest('form').get(0).reset();
        $('#foto').unwrap();         
        return false;
    }   
    reader.onload = function (e) {
        // get loaded data and render thumbnail.
        document.getElementById("foto").src = e.target.result;
        $("#foto").show(); 
    };

    // read the image file as a data URL.
    reader.readAsDataURL(this.files[0]);
};

;