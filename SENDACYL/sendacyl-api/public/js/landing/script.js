// JavaScript Document
$(window).load(function(){
    // will fade out the whole DIV that covers the website.
    jQuery(".preloader").fadeOut("slow");
});

jQuery(document).ready(function ($) {
    'use strict';

    //jQuery for page scrolling feature - requires jQuery Easing plugin
    $('.page-scroll').on('click', function (event) {
        var $anchor = $(this);
        $('html, body').stop().animate({
            scrollTop: $($anchor.attr('href')).offset().top
        }, 1500, 'easeInOutExpo');
        event.preventDefault();
    });


    // collapsed menu close on click
    $(document).on('click', '.navbar-collapse.in', function (e) {
        if ($(e.target).is('a')) {
            $(this).collapse('hide');
        }
    });
    
    // add white background to nav with scrolling
    if ($(window).scrollTop() > 50) {
        $(".sticky-navigation").addClass("NavBg");
    }
    else {
        $(".sticky-navigation").removeClass("NavBg");
    }  
    $(window).scroll(function(){                
        if ($(window).scrollTop() > 50) {
            $(".sticky-navigation").addClass("NavBg");
        }
        else {
            $(".sticky-navigation").removeClass("NavBg");
        }  
    });
     
     // screenshots carousel 
    jQuery(function() {

        jQuery('#allinone_carousel_sweet').allinone_carousel({
            skin: 'sweet',
            width: 1140,
            height: 800,
            responsive:true,
            autoPlay: 5,
            resizeImages:true,
            autoHideBottomNav:false,
            //easing:'easeOutBounce',
            numberOfVisibleItems:5,
            elementsHorizontalSpacing:180,
            elementsVerticalSpacing:50,
            verticalAdjustment:0,
            animationTime:0.5,
            circleLeftPositionCorrection:50,
            circleTopPositionCorrection:20,
            circleLineWidth:1,
            circleColor:"#29282B",
            behindCircleColor:"#999999",
            nextPrevMarginTop:25,
            bottomNavMarginBottom:-50
        });		
			
			
    });

// input , text area placeholder on browsers
    $('input, textarea').placeholder();

$("#shareIconsCountInside").jsSocials({
    url: "http://sendacyl.com",
    text: "SendaCYL - Rutas en Entornos Naturales por Castilla y León",
    showLabel: false,
    showCount: "inside",
    shares: ["twitter", "facebook", "googleplus", "pinterest"]
});

}); // end Document.ready

/* wow animation put it after Document.ready  */
wow = new WOW(
{
    mobile: false
});
wow.init();

// mailchimp start
if ($('.mailchimp').length>0) {
    /*  MAILCHIMP  */
    $('.mailchimp').ajaxChimp({
        callback: mailchimpCallback,
        url: "//mixdesigns.us4.list-manage.com/subscribe/post?u=d21d287a2a3620961a7419c49&amp;id=a4d425b5b6" //Replace this with your own mailchimp post URL. Don't remove the "". Just paste the url inside "".
    });
}
    
function mailchimpCallback(resp) {
    if (resp.result === 'success') {
        $('.subscription-success').html('<i class="icon_check_alt2"></i><br/>' + resp.msg).fadeIn(1000);
        $('.subscription-error').fadeOut(500);

    } else if(resp.result === 'error') {
        $('.subscription-error').html('<i class="icon_close_alt2"></i><br/>' + resp.msg).fadeIn(1000);
    }
}

// Contact Form
$(document).on('submit', '#contactForm', function (e) {
    e.preventDefault();
    var name = $("#name").val();
    var email = $("#email").val();
    var message = $("#message").val();
    var dataString = 'name=' + name + '&email=' + email + '&message=' + message;
    function isValidEmail(emailAddress) {
        var pattern = new RegExp(/^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i);
        return pattern.test(emailAddress);
    }
    if (isValidEmail(email) && (message.length > 1) && (name.length > 1)) {
        $.ajax({
            type: "POST",
            url: "sendmail.php",
            data: dataString,
            success: function () {
                $('.success').fadeIn(1000);
                $('.error').fadeOut(500);
            }
        });
    } else {
        $('.error').fadeIn(1000);
        $('.success').fadeOut(500);
    }
    return false;
});




