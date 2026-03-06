// CDL Blog — app.js

// Toggle password visibility
function togglePwd() {
  const input = document.getElementById('pwd');
  const icon  = document.getElementById('eyeIcon');
  if (!input) return;
  if (input.type === 'password') {
    input.type = 'text';
    icon.classList.replace('fa-eye','fa-eye-slash');
  } else {
    input.type = 'password';
    icon.classList.replace('fa-eye-slash','fa-eye');
  }
}

// Character counter for article content textarea
document.addEventListener('DOMContentLoaded', () => {
  const content = document.getElementById('content');
  const counter = document.getElementById('char-counter');
  if (content && counter) {
    const update = () => counter.textContent = content.value.length + ' caractères';
    content.addEventListener('input', update);
    update();
  }

  // Auto-hide alerts after 5s
  document.querySelectorAll('.alert').forEach(el => {
    setTimeout(() => { el.style.transition='opacity .5s'; el.style.opacity='0'; }, 5000);
  });
});
