# Uso: abra PowerShell e execute este script. Ele:
# - Substitui "package ...telas" por "package ...ui" em arquivos .java
# - Atualiza imports que referenciam ".telas" para ".ui"
# - Move arquivos de pastas \telas\ para \ui\
# - Cria backups *.bak dos arquivos alterados

$root = "c:\Users\leona\OneDrive\Documentos\GitHub\project-MARKBLUE---POO\src"
Write-Host "Raiz: $root"

# 1) Atualiza arquivos que estão dentro de pastas 'telas' (altera package e move arquivo)
Get-ChildItem -Path $root -Recurse -Filter *.java | ForEach-Object {
    $full = $_.FullName
    $text = Get-Content -Raw -LiteralPath $full
    $updated = $text -replace 'package\s+br\.edu\.cs\.poo\.ac\.ordem\.telas', 'package br.edu.cs.poo.ac.ordem.ui'
    $updated = $updated -replace 'import\s+br\.edu\.cs\.poo\.ac\.ordem\.telas', 'import br.edu.cs.poo.ac.ordem.ui'

    if ($text -ne $updated) {
        # backup
        Copy-Item -LiteralPath $full -Destination ($full + ".bak") -Force
        # compute new path (move \telas\ -> \ui\)
        if ($full -match '\\telas\\') {
            $newFull = $full -replace '\\telas\\', '\\ui\\'
        } else {
            $newFull = $full
        }
        $newDir = Split-Path $newFull -Parent
        New-Item -ItemType Directory -Path $newDir -Force | Out-Null
        # grava no novo local
        Set-Content -LiteralPath $newFull -Value $updated -Encoding UTF8
        if ($newFull -ne $full) {
            Remove-Item -LiteralPath $full -Force
            Write-Host "Movido: $full -> $newFull"
        } else {
            Write-Host "Atualizado: $full"
        }
    }
}

# 2) Atualiza quaisquer outros arquivos do projeto que importem o pacote telas
Get-ChildItem -Path $root -Recurse -Filter *.java | ForEach-Object {
    $full = $_.FullName
    $text = Get-Content -Raw -LiteralPath $full
    $updated = $text -replace 'import\s+br\.edu\.cs\.poo\.ac\.ordem\.telas', 'import br.edu.cs.poo.ac.ordem.ui'
    if ($text -ne $updated) {
        Copy-Item -LiteralPath $full -Destination ($full + ".bak_imports") -Force
        Set-Content -LiteralPath $full -Value $updated -Encoding UTF8
        Write-Host "Imports atualizados em: $full"
    }
}

Write-Host "Concluído. Verifique arquivos .bak caso precise reverter. Depois, recompile o projeto."
