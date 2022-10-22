#include <stdio.h>

int main () {

    unsigned long long f[61];
    unsigned int T, i, j;

    f[0] = 0;
    f[1] = 1;

    for (i = 2; i < 61; i++) 
        f[i] = f[i - 1] + f[i - 2];

    
    scanf("%u", &T);

    unsigned int r[T];

    for (i = 0; i < T; i++) {
        scanf("%u", &r[i]);
    }

    for (i = 0; i < T; i++) {
        if (r[i] > 60 || r[i] < 0) {
            continue;
        }
        printf("Fib(%u) = %llu\n", r[i], f[r[i]]);
    }

    return 0;
}