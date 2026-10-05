document.addEventListener("DOMContentLoaded", function() {
    
    const sidebarCollapseBtn = document.getElementById('sidebarCollapse');
    const sidebar = document.getElementById('sidebar');

    if (sidebarCollapseBtn && sidebar) {
        sidebarCollapseBtn.addEventListener('click', function() {
            sidebar.classList.toggle('active');
        });
    }

});
