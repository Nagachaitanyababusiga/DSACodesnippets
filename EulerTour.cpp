#include <bits/stdc++.h>
using namespace std;
//link: https://cses.fi/problemset/task/1138/

struct EulerPathSegTree {
    vector<int> start, end;
    vector<long long> segTree;
    vector<int> vals;
    vector<int> euler;
    int size;
    int timer = 0;

    EulerPathSegTree(vector<int>& values, vector<vector<int>>& tree) {
        vals = values;
        int n = vals.size();

        start.resize(n);
        end.resize(n);

        euler.reserve(2 * n);

        dfs(0, -1, tree);

        size = euler.size();
        segTree.resize(4 * size);

        build(0, size - 1, 0);
    }

    void dfs(int node, int parent, vector<vector<int>>& tree) {
        start[node] = timer;
        euler.push_back(vals[node]);
        timer++;

        for (int child : tree[node]) {
            if (child == parent) continue;
            dfs(child, node, tree);
        }

        euler.push_back(-vals[node]);
        end[node] = timer;
        timer++;
    }

    void build(int l, int r, int pos) {
        if (l == r) {
            segTree[pos] = euler[l];
            return;
        }

        int mid = (l + r) / 2;
        int lc = 2 * pos + 1;
        int rc = 2 * pos + 2;

        build(l, mid, lc);
        build(mid + 1, r, rc);

        segTree[pos] = segTree[lc] + segTree[rc];
    }

    void update(int node, int newVal) {
        updateHelp(0, size - 1, 0, start[node], newVal);
        updateHelp(0, size - 1, 0, end[node], -newVal);
    }

    void updateHelp(int l, int r, int pos, int idx, long long val) {
        if(r<idx||l>idx) return;
        if (l == r) {
            segTree[pos] = val;
            return;
        }

        int mid = (l + r) / 2;
        int lc = 2 * pos + 1;
        int rc = 2 * pos + 2;

        updateHelp(l, mid, lc, idx, val);
        updateHelp(mid + 1, r, rc, idx, val);

        segTree[pos] = segTree[lc] + segTree[rc];
    }

    long long query(int node) {
        return queryHelp(0, size - 1, 0, 0, start[node]);
    }

    long long queryHelp(int l, int r, int pos, int ql, int qr) {
        if (qr < l || ql > r) return 0;

        if (ql <= l && r <= qr) return segTree[pos];

        int mid = (l + r) / 2;
        int lc = 2 * pos + 1;
        int rc = 2 * pos + 2;

        return queryHelp(l, mid, lc, ql, qr) +
               queryHelp(mid + 1, r, rc, ql, qr);
    }
};

int main() {
    ios::sync_with_stdio(false);
    cin.tie(NULL);

    int n, q;
    cin >> n >> q;

    vector<int> vals(n);
    for (int i = 0; i < n; i++) cin >> vals[i];

    vector<vector<int>> tree(n);
    for (int i = 0; i < n - 1; i++) {
        int a, b;
        cin >> a >> b;
        a--; b--;
        tree[a].push_back(b);
        tree[b].push_back(a);
    }

    EulerPathSegTree segTree(vals, tree);

    while (q--) {
        int type;
        cin >> type;

        if (type == 1) {
            int node, val;
            cin >> node >> val;
            segTree.update(node - 1, val);
        } else {
            int node;
            cin >> node;
            cout << segTree.query(node - 1) << "\n";
        }
    }

    return 0;
}