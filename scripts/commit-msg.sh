#!/bin/bash

HOOK_PATH=".git/hooks/commit-msg"

install_hook() {
  mkdir -p .git/hooks
  cat > "$HOOK_PATH" <<'EOF'
#!/bin/bash
commit_message_file=$1
commit_message=$(cat "$commit_message_file")

if ! echo "$commit_message" | grep -qE "^(feat|fix|hotfix|style|docs|refactor|test|chore): .+ \(#\d+\)$"; then
  echo "🚨 올바르지 않은 형식의 커밋 메시지입니다. 🚨"
  echo "형식: <타입>: 메시지 (#이슈번호)"
  echo "예시: feat: 로그인 기능 추가 (#123)"
  exit 1
fi
EOF

  chmod +x "$HOOK_PATH"
  echo "✅ commit-msg 훅이 설치되었습니다."
}

remove_hook() {
  if [ -f "$HOOK_PATH" ]; then
    rm "$HOOK_PATH"
    echo "🗑️ commit-msg 훅이 제거되었습니다."
  else
    echo "ℹ️ commit-msg 훅이 존재하지 않습니다."
  fi
}

check_status() {
  if [ -x "$HOOK_PATH" ]; then
    echo "✅ commit-msg 훅이 설치되어 있습니다"
  else
    echo "❌ commit-msg 훅이 설치되어 있지 않습니다."
  fi
}

# 커맨드 분기
case "$1" in
  --remove) remove_hook ;;
  --status) check_status ;;
  *) install_hook ;;  # 기본은 설치
esac
