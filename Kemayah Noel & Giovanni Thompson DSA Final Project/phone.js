// SEARCH -------------------------------------------------------
function filterList() {
  const input = document.getElementById("myInput").value.toUpperCase();
  const li = document.querySelectorAll("#myUL li");

  li.forEach(item => {
    const name = item.querySelector(".name").textContent.toUpperCase();
    item.style.display = name.includes(input) ? "" : "none";
  });

  clearActiveLetters();
}

// SORTING -------------------------------------------------------
function sortList(ascending = true) {
  const ul = document.getElementById("myUL");
  const items = Array.from(ul.children);

  items.sort((a, b) => {
    const A = a.querySelector(".name").textContent.replace("Name:", "").trim().toLowerCase();
    const B = b.querySelector(".name").textContent.replace("Name:", "").trim().toLowerCase();
    return ascending ? A.localeCompare(B) : B.localeCompare(A);
  });

  items.forEach(i => ul.appendChild(i));
}

// ALPHABET FILTER ----------------------------------------------
function setupAlphabetFilter() {
  const container = document.getElementById("alphabetFilter");
  const alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".split("");

  alphabet.forEach(letter => {
    const btn = document.createElement("button");
    btn.textContent = letter;
    btn.onclick = () => filterByLetter(letter, btn);
    container.appendChild(btn);
  });

  const allBtn = document.createElement("button");
  allBtn.textContent = "All";
  allBtn.onclick = showAll;
  container.appendChild(allBtn);
}

function filterByLetter(letter, btn) {
  const li = document.querySelectorAll("#myUL li");

  li.forEach(item => {
    const name = item.querySelector(".name").textContent.toUpperCase();
    item.style.display = name.startsWith(`NAME: ${letter}`) ? "" : "none";
  });

  clearActiveLetters();
  btn.classList.add("active");
  document.getElementById("myInput").value = "";
}

function clearActiveLetters() {
  document.querySelectorAll(".alphabet-filter button").forEach(b => b.classList.remove("active"));
}

function showAll() {
  document.querySelectorAll("#myUL li").forEach(li => li.style.display = "");
  clearActiveLetters();
  document.getElementById("myInput").value = "";
}

// INIT
setupAlphabetFilter();
