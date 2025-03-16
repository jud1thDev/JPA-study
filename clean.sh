#!/bin/bash

# 현재 스크립트가 위치한 디렉토리로 이동
cd "${0%/*}" 2>/dev/null
echo "$PWD/${0##*/}"

# 불필요한 IntelliJ 설정 파일 및 빌드 디렉토리 삭제
rm -rf .idea
rm -rf target
rm -rf out
rm -rf logs

# Maven 프로젝트 클린 빌드
mvn clean
