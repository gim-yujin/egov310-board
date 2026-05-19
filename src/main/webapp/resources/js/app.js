var egovApp = (function ($) {
    'use strict';

    return {
        bindJoinForm: function (idCheckUrl) {
            var $id = $('#memberId');
            var $result = $('#idCheckResult');
            var idChecked = false;
            $id.on('input', function () { idChecked = false; $result.text(''); });

            $('#btnIdCheck').on('click', function () {
                var memberId = $id.val();
                if (!memberId) { $result.text('아이디를 입력하세요.').css('color', '#c0392b'); return; }
                $.get(idCheckUrl, { memberId: memberId }, function (data) {
                    if (data === 'OK') {
                        idChecked = true;
                        $result.text('사용 가능한 아이디입니다.').css('color', '#27ae60');
                    } else {
                        idChecked = false;
                        $result.text('이미 사용 중인 아이디입니다.').css('color', '#c0392b');
                    }
                });
            });

            $('#joinForm').on('submit', function (e) {
                if (!idChecked) {
                    alert('아이디 중복확인을 해주세요.');
                    e.preventDefault();
                    return false;
                }
                if ($('#password').val() !== $('#passwordConfirm').val()) {
                    alert('비밀번호가 일치하지 않습니다.');
                    e.preventDefault();
                    return false;
                }
            });
        },

        bindBoardForm: function () {
            $('#boardForm').on('submit', function (e) {
                var title = $.trim($(this).find('input[name=title]').val());
                var content = $.trim($(this).find('textarea[name=content]').val());
                if (!title || !content) {
                    alert('제목과 내용을 모두 입력하세요.');
                    e.preventDefault();
                    return false;
                }
            });
        },

        bindDeleteConfirm: function () {
            $('#deleteForm').on('submit', function (e) {
                if (!confirm('정말 삭제하시겠습니까?')) {
                    e.preventDefault();
                    return false;
                }
            });
        }
    };
})(jQuery);
